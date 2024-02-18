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
      questionText: ['', Validators.required],
    });
  }

  ngOnInit(): void {}

  createQuestion(): void {
    if (this.createForm.valid) {
      const newQuestion = {
        questionText: this.createForm.get('questionText')?.value || '',
      };

      this.questionService.createQuestion(newQuestion).subscribe(
        () => {
          Swal.fire('Success', 'Question created successfully!', 'success');
          this.router.navigate(['/dashboard']);
        },
        (error) => {
          console.error('Error creating question:', error);
          Swal.fire('Error', 'An error occurred while creating the question.', 'error');
        }
      );
    }
  }
}
