import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthResponseData } from 'src/app/models/auth-response-data';
import { Registration } from 'src/app/models/registration';
import { CityResponse } from 'src/app/models/response/city-response';
import { AuthService } from 'src/app/services/auth/auth.service';
import { CityService } from 'src/app/services/cities/city.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit{
  imageUrl1: string = 'assets/img/young.png';
  imageUrl2: string = 'assets/img/man.png';
  registrationForm: FormGroup;
  cities: CityResponse[] = [];


  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router, private cityService: CityService) {
    this.registrationForm = this.fb.group({
      firstName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      lastName: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      telephone: ['', [Validators.required, Validators.minLength(9), Validators.maxLength(15)]],
      bio: ['', [Validators.maxLength(255)]],
      budget: ['', [Validators.min(0)]],
      occupation: [, [Validators.required]],
      gender: [, [Validators.required]],
      birthdate: ['', [Validators.required]],
      currentCityId: [, [Validators.required]],
      desiredCityId: [, [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.cityService.getAllCities().subscribe(
      cities => {
        this.cities = cities;
      },
      error => {
        console.error('Error fetching cities:', error);
      }
    );
  }

  get formControls() {
    return this.registrationForm.controls;
  }

  register() {
    const registerData: Registration = this.registrationForm.value;
    console.log(registerData);
    

    this.authService.register(registerData).subscribe(
      (response: AuthResponseData) => {
        console.log('Registration successful', response);
        Swal.fire('Success', 'Registration successful!', 'success');
      },
      (error) => {
        console.error('Error creating question:', error);

        if (error instanceof HttpErrorResponse && error.status === 400) {
          if (error.error && error.error.error === 'Validation error' && error.error.message) {
            Swal.fire('Error', error.error.message, 'error');
          } else {
            Swal.fire('Error', 'An error occurred while creating the question.', 'error');
          }
        } else {
          Swal.fire('Error', 'An unexpected error occurred.', 'error');
        }
      }
    );
  }
}
