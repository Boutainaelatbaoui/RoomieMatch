import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { StorageService } from '../storage/storage.service';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { UserResponse } from 'src/app/models/response/user-response';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = environment.apiUrl;

  constructor(
    private http: HttpClient,
    private storageService: StorageService,
    private router: Router
  ) {}

  getAllUsers(): Observable<UserResponse[]> {
    return this.http.get<UserResponse[]>(`${this.apiUrl}/roomates`);
  }

  getRoommateDetails(id: number): Observable<UserResponse> {
    return this.http.get<UserResponse>(`${this.apiUrl}/roomates/${id}`);
  }
}
