import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { StorageService } from '../storage/storage.service';
import { Router } from '@angular/router';
import { NotificationResponseDTO } from 'src/app/models/response/notification-response';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private apiUrl = environment.apiUrl;

  constructor(
    private http: HttpClient,
    private storageService: StorageService,
    private router: Router
  ) {}

  getNotificationsByRecipient(): Observable<NotificationResponseDTO[]> {
    return this.http.get<NotificationResponseDTO[]>(`${this.apiUrl}/notifications/recipient`);
  }

  markNotificationAsRead(id: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/notifications/${id}/read`, {});
  }
}
