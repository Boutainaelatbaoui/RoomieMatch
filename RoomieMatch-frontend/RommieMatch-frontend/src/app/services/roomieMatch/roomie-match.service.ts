import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { StorageService } from '../storage/storage.service';
import { Router } from '@angular/router';
import { RoommateMatchDTO } from 'src/app/models/response/roommate-match-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RoomieMatchService {
  private apiUrl = environment.apiUrl;

  constructor(
    private http: HttpClient,
    private storageService: StorageService,
    private router: Router
  ) {}

  findRoommatesForUser(userEmail: string): Observable<RoommateMatchDTO[]> {
    return this.http.get<RoommateMatchDTO[]>(`${this.apiUrl}/roommate-matches/user/${userEmail}`);
  }
}
