import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { StorageService } from '../storage/storage.service';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { UserResponse } from 'src/app/models/response/user-response';
import { Role } from 'src/app/models/role';
import { Registration } from 'src/app/models/registration';

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

  getUserByEmail(email: string): Observable<UserResponse> {
    return this.http.get<UserResponse>(`${this.apiUrl}/roomates/email/${email}`);
  }

  getUserById(id: number): Observable<UserResponse> {
    return this.http.get<UserResponse>(`${this.apiUrl}/roomates/${id}`);
  }

  updateUserDetailsByEmail(email: string, request: any): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/roomates/${email}`, request);
  }

  searchUsersByName(name: string): Observable<UserResponse[]> {
    const url = `${this.apiUrl}/roomates/search?name=${name}`;
    return this.http.post<UserResponse[]>(url, {});
  }

  getRoles(): Observable<Role[]> {
    return this.http.get<Role[]>(`${this.apiUrl}/admin/roles`);
  }

  updateMemberRole(memberId: number, roleId: number): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/admin/updateRole/${memberId}/${roleId}`, {});
  }

  getUsersWithAcceptedRequests(): Observable<UserResponse[]> {
    return this.http.get<UserResponse[]>(`${this.apiUrl}/roomates/accepted`);
  }
}
