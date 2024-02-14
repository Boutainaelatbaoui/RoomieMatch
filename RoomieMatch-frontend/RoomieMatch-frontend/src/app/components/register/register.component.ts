import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthResponseData } from 'src/app/models/auth-response-data';
import { Registration } from 'src/app/models/registration';
import { AuthService } from 'src/app/services/auth/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  imageUrl1: string = 'assets/img/young.png';
  imageUrl2: string = 'assets/img/man.png';
  registrationForm: FormGroup;

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.registrationForm = this.fb.group({
      name: ['', Validators.required],
      familyName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      nationality: ['', Validators.required],
      identityDocument: ['PASSPORT', Validators.required],
      identityNumber: ['', Validators.required],
    });
  }

  get formControls() {
    return this.registrationForm.controls;
  }

  register() {
    if (this.registrationForm.invalid) {
      let errorMessage = '';
  
      if (this.formControls['name'].hasError('required')) {
        errorMessage += 'First name is required. ';
      }
  
      if (this.formControls['familyName'].hasError('required')) {
        errorMessage += 'family name is required. ';
      }
  
      if (this.formControls['email'].hasError('required')) {
        errorMessage += 'Email is required. ';
      } else if (this.formControls['email'].hasError('email')) {
        errorMessage += 'Email is not valid. ';
      }
  
      if (this.formControls['password'].hasError('required')) {
        errorMessage += 'Password is required. ';
      }
      if (this.formControls['nationality'].hasError('required')) {
        errorMessage += 'Nationality is required. ';
      }
      if (this.formControls['identityDocument'].hasError('required')) {
        errorMessage += 'Identity Document is required. ';
      }
      if (this.formControls['identityNumber'].hasError('required')) {
        errorMessage += 'Identity Number is required. ';
      }
  
      Swal.fire('Warning!', errorMessage, 'warning');
      return;
    }
  
    const registerData: Registration = this.registrationForm.value;
  
    this.authService.register(registerData).subscribe(
      (response: AuthResponseData) => {
        console.log('Registration successful', response);
        Swal.fire('Error!', 'Registration successfull But Your Account is Not Activated Yet !!!', 'error');
      },
      error => {
        console.error('Registration failed', error);
        Swal.fire('Error!', 'Registration failed', 'error');
      }
    );
  }
  
}
