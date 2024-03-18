import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { StorageService } from '../storage/storage.service';
import { Router } from '@angular/router';
import { QuestionnaireRequest } from 'src/app/models/request/questionnaire-request';
import { Observable, catchError } from 'rxjs';
import { QuestionnaireResponse } from 'src/app/models/response/questionnaire-response';

@Injectable({
  providedIn: 'root'
})
export class QuestionnaireService {
  private apiUrl = environment.apiUrl;

  constructor(
    private http: HttpClient,
    private storageService: StorageService,
    private router: Router
  ) {}

  saveResponse(request: QuestionnaireRequest[]): Observable<QuestionnaireResponse[]> {
    console.log('Request:', request);
    
    return this.http.post<QuestionnaireResponse[]>(`${this.apiUrl}/questionnaire`, request)
      .pipe(
        catchError((error) => {
          console.error('Error saving questionnaire responses:', error);
          throw error;
        })
      );
  }

}
