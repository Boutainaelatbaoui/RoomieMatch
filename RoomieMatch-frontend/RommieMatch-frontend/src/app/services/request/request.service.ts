import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { RequestResponse } from 'src/app/models/response/request-response';
import { RequestRequest } from 'src/app/models/request/request-request';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  sendRequest(request: RequestRequest): Observable<RequestResponse> {
    return this.http.post<RequestResponse>(`${this.apiUrl}/send`, request)
      .pipe(
        catchError(this.handleError)
      );
  }

  acceptRequest(requestId: number, userEmail: string): Observable<RequestResponse> {
    return this.http.post<RequestResponse>(`${this.apiUrl}/requests/accept/${requestId}/${userEmail}`, {})
      .pipe(
        catchError(this.handleError)
      );
  }

  rejectRequest(requestId: number, userEmail: string): Observable<RequestResponse> {
    return this.http.post<RequestResponse>(`${this.apiUrl}/requests/reject/${requestId}/${userEmail}`, {})
      .pipe(
        catchError(this.handleError)
      );
  }

  getReceivedRequests(userEmail: string): Observable<RequestResponse[]> {
    return this.http.get<RequestResponse[]>(`${this.apiUrl}/requests/received/${userEmail}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  getSentRequests(userEmail: string): Observable<RequestResponse[]> {
    return this.http.get<RequestResponse[]>(`${this.apiUrl}/requests/sent/${userEmail}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  getAllRequests(): Observable<RequestResponse[]> {
    return this.http.get<RequestResponse[]>(this.apiUrl)
      .pipe(
        catchError(this.handleError)
      );
  }

  getSenderRequestsByStatus(senderEmail: string, status: string): Observable<RequestResponse[]> {
    return this.http.get<RequestResponse[]>(`${this.apiUrl}/requests/recipient/${senderEmail}/status/${status}`)
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: any): Observable<never> {
    console.error('An error occurred:', error);
    throw error;
  }
}
