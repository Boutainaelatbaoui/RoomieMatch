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
        Swal.fire('Success!', 'Registration successful', 'success');
        this.router.navigate(['/']);
      },
      error : err => {
        this.errorMessage = err;
        console.log(err);
      }
    })
  }
}
