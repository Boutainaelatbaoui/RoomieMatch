import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { CityResponse } from 'src/app/models/response/city-response';

@Injectable({
  providedIn: 'root'
})
export class CityService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  getAllCities(): Observable<CityResponse[]> {
    return this.http.get<CityResponse[]>(`${this.apiUrl}/cities`);
  }
}
