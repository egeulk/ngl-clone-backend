package com.ngl.clone.controllers;

import com.ngl.clone.Readquestiondto;
import com.ngl.clone.entities.Question;
import com.ngl.clone.questionDTO;
import com.ngl.clone.request.JwtResponse;
import com.ngl.clone.request.LoginRequest;
import com.ngl.clone.security.JwtUtils;
import com.ngl.clone.services.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin(origins = {"*","http://localhost:4200", "https://nursimayasor.com"}, methods = {RequestMethod.GET, RequestMethod.POST}) // Apply CORS at class level
//     @CrossOrigin(origins = "http://localhost:4200")
public class RestController {

    private static int GOSTERILEN_SORU_SAYISI=24;
    private QuestionService questionService;
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setMessagingTemplate(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/question")
    public Page<Question> getQuestions(@RequestParam(name = "page", defaultValue="0") int page) {
        Pageable pageable = PageRequest.of(page, GOSTERILEN_SORU_SAYISI, Sort.by(Sort.Direction.DESC, "id"));
        return questionService.getAllQuestions(pageable);
    }
    @GetMapping("/question/{id}")
    public Question getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }

    @PostMapping("/question")
    public Question sendQuestion(@RequestBody questionDTO questionDTO) {

        Question question = questionService.saveQuestion(questionDTO);
        if (question!=null) {
            messagingTemplate.setUserDestinationPrefix("/topic");
            messagingTemplate.convertAndSend("/topic/newQuestion", question);
        }
        return question;
    }

    @MessageMapping("/ws")
    @SendTo("/topic/newQuestion")
    public Question send(Question question) throws Exception {
        return question;
    }

    @PostMapping("/question/read")
    public void markQuestionAsRead(@RequestBody Readquestiondto id) {
        questionService.markQuestionAsRead(id.getId());
    }

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
               // userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }


}
