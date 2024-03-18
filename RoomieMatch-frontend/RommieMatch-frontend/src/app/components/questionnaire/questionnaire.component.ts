import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { QuestionResponse } from 'src/app/models/response/question-response';
import { QuestionService } from 'src/app/services/questions/question.service';
import { StorageService } from 'src/app/services/storage/storage.service';
import Swal from 'sweetalert2';
import { QuestionnaireRequest } from 'src/app/models/request/questionnaire-request';
import { QuestionnaireService } from 'src/app/services/questionnaire/questionnaire.service';

@Component({
  selector: 'app-questionnaire',
  templateUrl: './questionnaire.component.html',
  styleUrls: ['./questionnaire.component.css']
})
export class QuestionnaireComponent {
  questionnaireForm: FormGroup = this.fb.group({});
  questions: QuestionResponse[] = [];
  selectedOptions: { [questionId: number]: number } = {};
  imageUrl1: string = 'assets/img/young.png';
  imageUrl2: string = 'assets/img/man.png';
  imageUrl5: string = 'assets/img/dotted.png';


  constructor(
    private fb: FormBuilder,
    private questionService: QuestionService,
    private storageService: StorageService,
    private questionnaireService: QuestionnaireService
  ) {}

  ngOnInit(): void {
    this.fetchQuestions();
  }

  fetchQuestions(): void {
    this.questionService.getAllQuestions().subscribe(
      (response) => {
        this.questions = response;
        this.createFormControls();
      },
      (error) => {
        console.error('Error fetching questions:', error);
      }
    );
  }

  createFormControls(): void {
    this.questions.forEach((question) => {
      this.questionnaireForm.addControl(`question${question.id}`, this.fb.control('', Validators.required));
    });
  }

  selectOption(questionId: number, optionId: number): void {
    console.log('Selected option for question', questionId, ':', optionId);
    
    this.selectedOptions[questionId] = optionId;
  }

  submit(): void {
    const responses: QuestionnaireRequest[] = [];
    for (const question of this.questions) {
      const selectedOptionId = this.selectedOptions[question.id];
      console.log('Selected option for question', question.id, ':', selectedOptionId);
      
      if (selectedOptionId !== undefined) {
        responses.push({
          userId: this.storageService.getSavedUser()?.id || 0,
          questionId: question.id,
          choiceId: selectedOptionId
        });
      } else {
        Swal.fire('Error', 'Please select an option for all questions before submitting the questionnaire.', 'error');
        return;
      }
    }

    this.questionnaireService.saveResponse(responses).subscribe(
      (response) => {
        console.log('Responses saved successfully:', response);
        Swal.fire('Success', 'Questionnaire submitted successfully!', 'success');
      },
      (error) => {
        console.error('Error saving responses:', error);
        Swal.fire('Error', 'An error occurred while submitting the questionnaire.', 'error');
      }
    );
  }
}
