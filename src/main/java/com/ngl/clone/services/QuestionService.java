package com.ngl.clone.services;

import com.ngl.clone.entities.Question;
import com.ngl.clone.questionDTO;
import com.ngl.clone.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Page<Question> getAllQuestions(Pageable pageable) {

        return questionRepository.findAll(pageable);
    }

    public Question saveQuestion(questionDTO questionDTO) {
        Question question = new Question();
        question.setQuestionText(questionDTO.getQuestionText());
        return questionRepository.save(question);
    }


    public Question getQuestionById(Long id) {
        return questionRepository.findById(id);
    }

    @Transactional
    public void markQuestionAsRead(Long id) {
        questionRepository.updateIsReadById(id, true);
    }
}
