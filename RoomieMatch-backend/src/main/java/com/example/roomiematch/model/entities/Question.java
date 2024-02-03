package com.example.roomiematch.model.entities;

import lombok.*;
import jakarta.persistence.*;
import java.util.List;

@Getter
@Builder
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionText;

    @OneToMany(mappedBy = "question")
    private List<UserQuestionPreference> userQuestionPreferences;

    @OneToMany(mappedBy = "question")
    private List<QuestionnaireResponse> questionnaireResponses;

    @OneToMany(mappedBy = "question")
    private List<Choice> choices;
}

