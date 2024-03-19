package com.example.roomiematch.controller;

import com.example.roomiematch.model.dto.request.QuestionnaireResponseRequestDTO;
import com.example.roomiematch.model.dto.response.QuestionnaireResponseResponseDTO;
import com.example.roomiematch.service.IQuestionnaireResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questionnaire")
public class QuestionnaireResponseController {
    private final IQuestionnaireResponseService responseService;

    @Autowired
    public QuestionnaireResponseController(IQuestionnaireResponseService responseService) {
        this.responseService = responseService;
    }

    @PostMapping
    public ResponseEntity<List<QuestionnaireResponseResponseDTO>> saveResponses(@RequestBody List<QuestionnaireResponseRequestDTO> requests) {
        return new ResponseEntity<>(responseService.saveResponses(requests), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<QuestionnaireResponseResponseDTO>> getAllResponses() {
        return new ResponseEntity<>(responseService.getAllResponses(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionnaireResponseResponseDTO> getResponseById(@PathVariable("id") Long id) {
        QuestionnaireResponseResponseDTO responseDTO = responseService.getResponseById(id);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/user/{userEmail}")
    public ResponseEntity<List<QuestionnaireResponseResponseDTO>> getAllResponsesByUserEmail(@PathVariable String userEmail) {
        return ResponseEntity.ok(responseService.getAllResponsesByUserEmail(userEmail));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResponseById(@PathVariable("id") Long id) {
        responseService.deleteResponseById(id);
        return ResponseEntity.status(200).build();
    }
}
