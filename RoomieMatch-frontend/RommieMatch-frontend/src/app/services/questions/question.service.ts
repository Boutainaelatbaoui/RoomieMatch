import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { StorageService } from '../storage/storage.service';
import { Router } from '@angular/router';
import { QuestionResponse } from 'src/app/models/response/question-response';
import { Observable, catchError, throwError } from 'rxjs';
import { QuestionRequest } from 'src/app/models/request/question-request';
import { QuestionnaireRequest} from 'src/app/models/request/questionnaire-request';
import { QuestionnaireResponse } from 'src/app/models/response/questionnaire-response';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {
  private apiUrl = environment.apiUrl;

  constructor(
    private http: HttpClient,
    private storageService: StorageService,
    private router: Router
  ) {}

  getAllQuestions(): Observable<QuestionResponse[]> {
    return this.http.get<QuestionResponse[]>(`${this.apiUrl}/questions`);
  }

  getTenQuestions(): Observable<QuestionResponse[]> {
    return this.http.get<QuestionResponse[]>(`${this.apiUrl}/questions/ten`);
  }

  getQuestionById(id: number): Observable<QuestionResponse> {
    return this.http.get<QuestionResponse>(`${this.apiUrl}/questions/${id}`);
  }

  createQuestion(question: QuestionRequest): Observable<QuestionResponse> {
    return this.http.post<QuestionResponse>(`${this.apiUrl}/questions`, question);
  }

  updateQuestion(id: number, question: QuestionRequest): Observable<QuestionResponse> {
    return this.http.put<QuestionResponse>(`${this.apiUrl}/questions/${id}`, question);
  }  

  deleteQuestion(id: number): Observable<void> {
    const url = `${this.apiUrl}/questions/${id}`;
    return this.http.delete<void>(url).pipe(
      catchError((error) => {
        console.error('Error deleting question:', error);
        return throwError(error);
      })
    );
  }
}
