import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor as AngularHttpInterceptor,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { StorageService } from 'src/app/services/storage/storage.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import Swal from 'sweetalert2';
import { jwtDecode } from 'jwt-decode';

@Injectable()
export class HttpInterceptor implements AngularHttpInterceptor {
  private isRefreshing = false;

  constructor(
    private authService: AuthService,
    private storageService: StorageService
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = this.storageService.getSavedUser()?.token;

    if (token && !request.url.includes('refreshToken')) {
      console.log(token);
      
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(request).pipe(
      catchError(error => {
        if (error.status === 403 && this.isTokenExpired(token)) { 
            return this.handleUnauthorizedError(request, next);
        }
        return throwError(error);
      })
    );
  }

  private handleUnauthorizedError(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    if (!this.isRefreshing) {

      this.isRefreshing = true;
      return this.authService.refreshAccessToken().pipe(
        switchMap((newAccessToken: string) => {
          this.isRefreshing = false;
          console.log(newAccessToken);
          
          const newRequest = request.clone({
            setHeaders: {
              Authorization: `Bearer ${newAccessToken}`
            }
          });
          return next.handle(newRequest);
        }),
        catchError(refreshError => {
          this.isRefreshing = false;
          console.error('Failed to refresh access token', refreshError);
          this.authService.logout(); 
          return throwError(refreshError);
        })
      );
    } else {
      this.isRefreshing = false;
      return throwError('Refreshing access token in progress');
    }
  }

  private isTokenExpired(token: any): boolean {
    try {
      const decodedToken: any = jwtDecode(token);
      const currentTime = Date.now() / 1000;
  
      return decodedToken.exp < currentTime;
    } catch (error) {
      console.error('Error decoding token:', error);
      return true;
    }
  }
}
