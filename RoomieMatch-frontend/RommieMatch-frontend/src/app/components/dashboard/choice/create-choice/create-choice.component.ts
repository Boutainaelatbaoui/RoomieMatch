import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ChoiceRequest } from 'src/app/models/request/choice-request';
import { QuestionResponse } from 'src/app/models/response/question-response';
import { ChoiceService } from 'src/app/services/choice/choice.service';
import { QuestionService } from 'src/app/services/questions/question.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-choice',
  templateUrl: './create-choice.component.html',
  styleUrls: ['./create-choice.component.css']
})
export class CreateChoiceComponent implements OnInit {
  choiceForm: FormGroup = new FormGroup({});
  questions: QuestionResponse[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private choiceService: ChoiceService,
    private router: Router,
    private questionService: QuestionService
  ) { }

  ngOnInit(): void {
    this.initForm();
    this.loadQuestions();
  }

  initForm(): void {
    this.choiceForm = this.formBuilder.group({
      choiceText: ['', Validators.required],
      questionId: [null, Validators.required]
    });
  }

  loadQuestions(): void {
    this.questionService.getAllQuestions().subscribe(
      (questions: QuestionResponse[]) => {
        this.questions = questions;
      },
      (error) => {
        console.error('Error fetching questions:', error);
      }
    );
  }

  submitForm(): void {
    if (this.choiceForm.valid) {
      const choiceRequest: ChoiceRequest = this.choiceForm.value;
      this.choiceService.createChoice(choiceRequest).subscribe(
        (response: any) => {
          Swal.fire('Success', 'Choice created successfully!', 'success');
          this.router.navigate(['/choice']);
        },
        (error) => {
          console.error('Error creating question:', error);
  
          if (error instanceof HttpErrorResponse && error.status === 400) {
            if (error.error && error.error.error === 'Validation error' && error.error.message) {
              Swal.fire('Error', error.error.message, 'error');
            } else {
              Swal.fire('Error', 'An error occurred while creating the question.', 'error');
            }
          } else {
            Swal.fire('Error', 'An unexpected error occurred.', 'error');
          }
        }
      );
    }
  }
}
