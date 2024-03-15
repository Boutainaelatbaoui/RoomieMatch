import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { QuestionService } from 'src/app/services/questions/question.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-create-question',
  templateUrl: './create-question.component.html',
  styleUrls: ['./create-question.component.css']
})
export class CreateQuestionComponent implements OnInit {
  createForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private router: Router, private questionService: QuestionService) {
    this.createForm = this.formBuilder.group({
      questionText: ['', [Validators.required]],
    });
  }

  ngOnInit(): void {}

  get questionTextControl() {
    return this.createForm.get('questionText');
  }

  createQuestion(): void {
    if (this.createForm.valid) {
      const questionText = this.questionTextControl?.value;
  
      if (!questionText) {
        console.error('Invalid questionText:', questionText);
        return;
      }
  
      const newQuestion = { questionText };
  
      this.questionService.createQuestion(newQuestion).subscribe(
        () => {
          Swal.fire('Success', 'Question created successfully!', 'success');
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
