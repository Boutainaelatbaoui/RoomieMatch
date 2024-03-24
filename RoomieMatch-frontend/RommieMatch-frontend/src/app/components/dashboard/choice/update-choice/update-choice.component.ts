import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ChoiceRequest } from 'src/app/models/request/choice-request';
import { QuestionResponse } from 'src/app/models/response/question-response';
import { ChoiceService } from 'src/app/services/choice/choice.service';
import { QuestionService } from 'src/app/services/questions/question.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-update-choice',
  templateUrl: './update-choice.component.html',
  styleUrls: ['./update-choice.component.css']
})
export class UpdateChoiceComponent implements OnInit {
  updateForm: FormGroup = new FormGroup({});
  questions: QuestionResponse[] = [];
  choiceId: number = 0;

  constructor(
    private formBuilder: FormBuilder,
    private choiceService: ChoiceService,
    private router: Router,
    private questionService: QuestionService,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.initForm();
    this.route.params.subscribe(params => {
      this.choiceId = +params['id'];
      this.loadQuestions();
    });
  }
  
  initForm(): void {
    this.updateForm = this.formBuilder.group({
      choiceText: ['', Validators.required],
      questionId: [null, Validators.required]
    });
  }

  fetchChoiceDetails(): void {
    this.choiceService.getChoiceById(this.choiceId).subscribe(
      (choice) => {
        const questionId = this.findQuestionId(choice.question.questionText);
        console.log(questionId);
        
        if (questionId !== null) {
          this.updateForm.patchValue({
            choiceText: choice.choiceText,
            questionId: questionId,
          });
        } else {
          console.error('Error: Question not found');
        }
      },
      (error) => {
        console.error('Error fetching choice details:', error);
        this.router.navigate(['/choice']);
      }
    );
  }

  findQuestionId(questionText: string): number | null {
    console.log(questionText);
    
    const question = this.questions.find(q => q.questionText === questionText);
    console.log(question);
    
    return question ? question.id : null;
  }

  submitForm(): void {
    if (this.updateForm.valid) {
      const updatedChoice: ChoiceRequest = {
        choiceText: this.updateForm.get('choiceText')?.value || '',
        questionId: this.updateForm.get('questionId')?.value || null
      };
  
      this.choiceService.updateChoice(this.choiceId, updatedChoice).subscribe(
        (response) => {
          Swal.fire('Success', 'Choice updated successfully!', 'success');
          this.router.navigate(['/choice']);
        },
        (error) => {
          console.error('Error updating choice:', error);
  
          if (error instanceof HttpErrorResponse && error.status === 400) {
            if (error.error && error.error.error === 'Validation error' && error.error.message) {
              Swal.fire('Error', error.error.message, 'error');
            } else {
              Swal.fire('Error', 'An error occurred while updating the choice.', 'error');
            }
          } else {
            Swal.fire('Error', 'An unexpected error occurred.', 'error');
          }
        }
      );
    }
  }

  loadQuestions(): void {
    this.questionService.getAllQuestions().subscribe(
      (questions: QuestionResponse[]) => {
        this.questions = questions;
        this.fetchChoiceDetails();
      },
      (error) => {
        console.error('Error fetching questions:', error);
      }
    );
  }
}
