import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import { CityService } from 'src/app/services/cities/city.service';
import { StorageService } from 'src/app/services/storage/storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  imageUrl1: string = 'assets/img/young.png';
  imageUrl2: string = 'assets/img/man.png';
  registrationForm: FormGroup;
  preferenceForm: FormGroup;
  cities: any[] = []; // Update the type based on your CityResponse model

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router, private cityService: CityService, private storageService: StorageService) {
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

    this.preferenceForm = this.fb.group({
      smoking: ['', Validators.required],
      pets: ['', Validators.required],
      visitors: ['', Validators.required],
      partying: ['', Validators.required],
      sharingBedroom: ['', Validators.required],
      hasApartment: ['', Validators.required]
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

  submitForms() {
    if (this.registrationForm.valid && this.preferenceForm.valid) {
      const registrationData = this.registrationForm.value;
      const preferenceData = this.preferenceForm.value;
      
      this.authService.register(registrationData).subscribe(
        (registrationResponse: any) => {
          console.log('Registration successful', registrationResponse);
          
          const userId = this.storageService.getSavedUser()?.id;
  
          if (userId) {
            preferenceData.userId = userId;
  
            this.authService.createPreference(preferenceData).subscribe(
              (preferenceResponse: any) => {
                console.log('Preference submitted successfully', preferenceResponse);
                Swal.fire('Success', 'Registration and Preference submitted successfully!', 'success');
              },
              (preferenceError: HttpErrorResponse) => {
                console.error('Error submitting preference form:', preferenceError);
                let errorMessage = 'An error occurred while submitting the preference form.';
                if (preferenceError.status === 400 && preferenceError.error && preferenceError.error.message) {
                  errorMessage = preferenceError.error.message;
                }
                Swal.fire('Error', errorMessage, 'error');
              }
            );
          } else {
            Swal.fire('Error', 'User ID not found in the registration response.', 'error');
          }
        },
        (registrationError: HttpErrorResponse) => {
          console.error('Error submitting registration form:', registrationError);
          let errorMessage = 'An error occurred while submitting the registration form.';
          if (registrationError.status === 400 && registrationError.error && registrationError.error.message) {
            errorMessage = registrationError.error.message;
          }
          Swal.fire('Error', errorMessage, 'error');
        }
      );
    } else {
      Swal.fire('Error', 'Please fill out all the required fields in both forms.', 'error');
    }
  }
  
}
