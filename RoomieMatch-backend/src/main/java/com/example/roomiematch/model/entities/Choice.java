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
@Table(name = "choices")
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String choiceText;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToMany(mappedBy = "choice")
    private List<QuestionnaireResponse> questionnaireResponses;
}

