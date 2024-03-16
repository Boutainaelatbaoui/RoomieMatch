import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  imageUrl: string = 'assets/img/Picture.png';
  imageUrl1: string = 'assets/img/key.png';
  errorMessage! : string;

  constructor(private authService : AuthService,  private router : Router) {
  }

  onSubmitLogin(formLogin: NgForm) {
    if(!formLogin.valid){
      return;
    }
    const email = formLogin.value.email;
    const password = formLogin.value.password;

    this.authService.login(email, password).subscribe({
      next : userData => {
        Swal.fire('Success!', 'Login successful', 'success');
        this.router.navigate(['/']);
      },
      error : err => {
        if (err instanceof HttpErrorResponse && err.status === 400) {
          if (err.error && err.error.error === 'Validation error' && err.error.message) {
            Swal.fire('Error', err.error.message, 'error');
          } else {
            Swal.fire('Error', 'An error occurred while creating the question.', 'error');
          }
        } else {
          Swal.fire('Error', 'An unexpected error occurred.', 'error');
        }
      }
    })
  }
}
