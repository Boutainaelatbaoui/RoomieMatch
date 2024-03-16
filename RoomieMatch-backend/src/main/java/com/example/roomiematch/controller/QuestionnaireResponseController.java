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
    public ResponseEntity<QuestionnaireResponseResponseDTO> saveResponse(@RequestBody QuestionnaireResponseRequestDTO requestDTO) {
        return new ResponseEntity<>(responseService.saveResponse(requestDTO), HttpStatus.CREATED);
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

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<QuestionnaireResponseResponseDTO>> getAllResponsesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(responseService.getAllResponsesByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResponseById(@PathVariable("id") Long id) {
        responseService.deleteResponseById(id);
        return ResponseEntity.status(200).build();
    }
}
