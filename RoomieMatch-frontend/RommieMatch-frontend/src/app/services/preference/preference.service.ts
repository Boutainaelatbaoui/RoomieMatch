import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { StorageService } from '../storage/storage.service';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { PreferenceResponse } from 'src/app/models/response/preference-response';
import { PreferenceRequest } from 'src/app/models/request/preference-request';

@Injectable({
  providedIn: 'root'
})
export class PreferenceService {
  private apiUrl = environment.apiUrl;

  constructor(
    private http: HttpClient,
    private storageService: StorageService,
    private router: Router
  ) {}

  getPreferenceById(id: number): Observable<PreferenceResponse> {
    return this.http.get<PreferenceResponse>(`${this.apiUrl}/preferences/${id}`);
  }

  updatePreference(userEmail: string, requestDTO: PreferenceRequest): Observable<PreferenceResponse> {
    return this.http.put<PreferenceResponse>(`${this.apiUrl}/preferences/${userEmail}`, requestDTO);
  }
}
