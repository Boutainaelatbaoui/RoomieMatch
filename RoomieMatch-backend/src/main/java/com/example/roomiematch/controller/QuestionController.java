package com.example.roomiematch.controller;

import com.example.roomiematch.model.dto.response.QuestionResponseDTO;
import com.example.roomiematch.model.dto.request.QuestionRequestDTO;
import com.example.roomiematch.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final IQuestionService questionService;

    @Autowired
    public QuestionController(IQuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('CAN_READ')")
    public ResponseEntity<List<QuestionResponseDTO>> getAllQuestions() {
        List<QuestionResponseDTO> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/ten")
    @PreAuthorize("hasAnyAuthority('CAN_READ')")
    public ResponseEntity<List<QuestionResponseDTO>> getTenQuestions() {
        List<QuestionResponseDTO> questions = questionService.getTenQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CAN_READ')")
    public ResponseEntity<QuestionResponseDTO> getQuestionById(@PathVariable Long id) {
        QuestionResponseDTO question = questionService.getQuestionById(id);
        return ResponseEntity.ok(question);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CAN_UPDATE')")
    public ResponseEntity<QuestionRequestDTO> createQuestion(@RequestBody QuestionRequestDTO questionRequestDTO) {
        QuestionRequestDTO createdQuestion = questionService.createQuestion(questionRequestDTO);
        return ResponseEntity.ok(createdQuestion);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CAN_UPDATE')")
    public ResponseEntity<QuestionResponseDTO> updateQuestion(@PathVariable Long id, @RequestBody QuestionRequestDTO updatedQuestionRequestDTO) {
        QuestionResponseDTO updatedQuestion = questionService.updateQuestion(id, updatedQuestionRequestDTO);
        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CAN_UPDATE')")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.status(200).build();
    }
}
