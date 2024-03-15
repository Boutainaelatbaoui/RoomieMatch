import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { QuestionResponse } from 'src/app/models/response/question-response';
import { QuestionService } from 'src/app/services/questions/question.service';
import Swal from 'sweetalert2';
import { QuestionRequest } from 'src/app/models/request/question-request';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-update-question',
  templateUrl: './update-question.component.html',
  styleUrls: ['./update-question.component.css']
})
export class UpdateQuestionComponent implements OnInit {
  updateForm: FormGroup;
  questionId: number = 0;

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private questionService: QuestionService
  ) {
    this.updateForm = this.formBuilder.group({
      questionText: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.questionId = +params['id'];
      console.log(this.questionId);
      
      this.fetchQuestionDetails();
    });
  }
  
  fetchQuestionDetails(): void {
    this.questionService.getQuestionById(this.questionId).subscribe(
      (question) => {
        this.updateForm.patchValue({
          questionText: question.questionText,
        });
      },
      (error) => {
        console.error('Error fetching question details:', error);
        this.router.navigate(['/dashboard']);
      }
    );
  }

  updateQuestion(): void {
    if (this.updateForm.valid) {
      const updatedQuestion: QuestionRequest = {
        questionText: this.updateForm.get('questionText')?.value || '',
      };
  
      this.questionService.updateQuestion(this.questionId, updatedQuestion).subscribe(
        (response) => {
          Swal.fire('Success', 'Question updated successfully!', 'success');
          this.router.navigate(['/dashboard']);
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
