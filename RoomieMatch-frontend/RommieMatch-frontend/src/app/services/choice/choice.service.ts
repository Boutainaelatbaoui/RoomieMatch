import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { StorageService } from '../storage/storage.service';
import { Router } from '@angular/router';
import { Observable, catchError, throwError } from 'rxjs';
import { ChoiceResponse } from 'src/app/models/response/choice-response';
import { ChoiceRequest } from 'src/app/models/request/choice-request';

@Injectable({
  providedIn: 'root'
})
export class ChoiceService {
  private apiUrl = environment.apiUrl;

  constructor(
    private http: HttpClient,
    private storageService: StorageService,
    private router: Router
  ) {}

  getAllQuestions(): Observable<ChoiceResponse[]> {
    return this.http.get<ChoiceResponse[]>(`${this.apiUrl}/questions`);
  }

  getQuestionById(id: number): Observable<ChoiceResponse> {
    return this.http.get<ChoiceResponse>(`${this.apiUrl}/questions/${id}`);
  }

  createQuestion(question: ChoiceRequest): Observable<ChoiceResponse> {
    return this.http.post<ChoiceResponse>(`${this.apiUrl}/questions`, question);
  }

  updateQuestion(id: number, question: ChoiceRequest): Observable<ChoiceResponse> {
    return this.http.put<ChoiceResponse>(`${this.apiUrl}/questions/${id}`, question);
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
