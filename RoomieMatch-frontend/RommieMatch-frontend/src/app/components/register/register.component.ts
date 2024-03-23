import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CityResponse } from 'src/app/models/response/city-response';
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
  cities: CityResponse[] = [];

  occupationsMap = [
    { id: 1, text: 'Engineer' },
    { id: 2, text: 'Doctor' },
    { id: 3, text: 'Teacher' },
    { id: 4, text: 'Nurse' },
    { id: 5, text: 'Artist' },
    { id: 6, text: 'Lawyer' },
    { id: 7, text: 'Accountant' },
    { id: 8, text: 'Chef' },
    { id: 9, text: 'Police Officer' },
    { id: 10, text: 'Firefighter' }
  ];

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private cityService: CityService,
    private storageService: StorageService,
  ) {
    this.registrationForm = this.fb.group({
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      email: ['', [Validators.required]],
      password: ['', [Validators.required]],
      telephone: ['', [Validators.required]],
      bio: ['', [Validators.required]],
      budget: ['', [Validators.required]],
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
    this.loadCities();
  }

  loadCities(): void {
    this.cityService.getAllCities().subscribe(
      cities => {
        this.cities = cities;
      },
      error => {
        console.error('Error fetching cities:', error);
      }
    );
  }

  submitForms(): void {
    if (this.registrationForm.valid && this.preferenceForm.valid) {
      const registrationData = this.registrationForm.value;
      const preferenceData = this.preferenceForm.value;

      this.authService.register(registrationData).subscribe(
        () => {
          preferenceData.userId = this.storageService.getSavedUser()?.id;
          this.authService.createPreference(preferenceData).subscribe(
            () => {
              Swal.fire('Success', 'Registration and Preference submitted successfully!', 'success').then(() => {
                this.router.navigate(['/questionnaire']);
              });
            },
            (preferenceError: HttpErrorResponse) => {
              this.handleError('Error submitting preference form:', preferenceError);
            }
          );
        },
        (registrationError: HttpErrorResponse) => {
          this.handleError('Error submitting registration form:', registrationError);
        }
      );
    } else {
      Swal.fire('Error', 'Please fill out all the required fields in both forms.', 'error');
    }
  }

  private handleError(messagePrefix: string, error: HttpErrorResponse): void {
    console.error(messagePrefix, error);
    let errorMessage = `An error occurred: ${error.message || 'Unknown error'}`;
    if (error.error && error.error.message) {
      errorMessage = error.error.message;
    }
    Swal.fire('Error', errorMessage, 'error');
  }
}
