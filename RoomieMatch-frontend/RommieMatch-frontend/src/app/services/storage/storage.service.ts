import { Injectable } from '@angular/core';
import { User } from 'src/app/models/user';
import { jwtDecode } from 'jwt-decode';

const USER_KEY = 'authenticated-user';
const ACCESS_TOKEN_KEY = 'access-token';

@Injectable({
  providedIn: 'root'
})
export class StorageService {
  constructor() { }

  saveUser(user: User) {
    window.localStorage.removeItem(USER_KEY);
    window.localStorage.setItem(USER_KEY, JSON.stringify(user));
    console.log(user);
  }

  getSavedUser(): User | null {
    const data = window.localStorage.getItem(USER_KEY);
    if (data) {
      console.log(data);
      return JSON.parse(data);
    }
    return null;
  }

  getConnectedMemberId(): number | null {
    const user = this.getSavedUser();
    return user ? user.id : null;
  }

  saveAccessToken(token: string): void {
    localStorage.setItem(ACCESS_TOKEN_KEY, token);
  }

  getAccessToken(): string | null {
    return localStorage.getItem(ACCESS_TOKEN_KEY);
  }

  clean(): void {    
    localStorage.removeItem(USER_KEY);
    localStorage.removeItem(ACCESS_TOKEN_KEY);
  }

  decodeToken(): any {
    const token = this.getSavedUser()?.token || this.getAccessToken();
    if (token) {
      try {
        return jwtDecode(token);
      } catch (error) {
        console.error('Error decoding token:', error);
      }
    }
    return null;
  }

  getRoles(): string[] {
    const decodedToken = this.decodeToken();
    return decodedToken?.roles?.filter((role: string) => role.startsWith('ROLE_')) || [];
  }

  getPermissions(): string[] {
    const decodedToken = this.decodeToken();
    return decodedToken?.roles?.filter((role: string) => !role.startsWith('ROLE_')) || [];
  }

  getUserEnabled(): boolean {
    const decodedToken = this.decodeToken();
    return decodedToken?.enabled || false;
  }
}
