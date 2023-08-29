package com.ngl.clone.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.net.InetAddress;


@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @Column(name = "ip_address")
    private InetAddress ipAddress;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    public Question() {
    }

    public Question(String questionText, InetAddress  ipAddress, boolean isRead) {
        this.questionText = questionText;
        this.ipAddress = ipAddress;
        this.isRead = isRead;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public InetAddress  getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress  ipAddress) {
        this.ipAddress = ipAddress;
    }

    @JsonProperty("isRead")
    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }
}