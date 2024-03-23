import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
import { UserService } from 'src/app/services/user/user.service';
import { CityResponse } from 'src/app/models/response/city-response';
import { CityService } from 'src/app/services/cities/city.service';
import { StorageService } from 'src/app/services/storage/storage.service';
import { Registration } from 'src/app/models/registration';

@Component({
  selector: 'app-update-info',
  templateUrl: './update-info.component.html',
  styleUrls: ['./update-info.component.css']
})
export class UpdateInfoComponent implements OnInit {
  informationForm: FormGroup;
  userId: number = 0;
  cities: CityResponse[] = [];
  email: string = "";

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

  getOccupationText(code: number): string {
    const occupation = this.occupationsMap.find(occ => occ.id === code);
    return occupation ? occupation.text : 'Unknown';
  }

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private userService: UserService,
    private cityService: CityService,
    private storageService: StorageService,
  ) {
    this.informationForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      telephone: ['', Validators.required],
      bio: ['', Validators.required],
      budget: ['', Validators.required],
      occupation: ['', Validators.required],
      birthdate: ['', Validators.required],
      currentCityId: ['', Validators.required],
      desiredCityId: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.userId = +params['id'];
      this.fetchUserInfo();
    });
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

  fetchUserInfo(): void {
    this.userService.getUserById(this.userId).subscribe(
      (user: any) => {
        this.informationForm.patchValue({
          firstName: user.firstName,
          lastName: user.lastName,
          telephone: user.telephone,
          bio: user.bio,
          budget: user.budget,
          occupation: user.occupation,
          birthdate: user.birthdate,
          currentCityId: user.currentCity.id,
          desiredCityId: user.desiredCity.id,
        });        
      },
      (error) => {
        console.error('Error fetching user information:', error);
      });
  }

  updateInformation(): void {
    if (this.informationForm.valid) {
      const updatedInfo = this.informationForm.value;
      this.email = this.storageService.decodeToken().sub;

      this.userService.updateUserDetailsByEmail(this.email, updatedInfo).subscribe(
        (response: any) => {
          Swal.fire('Success', 'User information updated successfully!', 'success');
          this.router.navigate(['/profile']);
        },
        (error) => {
          console.error('Error updating user information:', error);

          if (error instanceof HttpErrorResponse && error.status === 400) {
            Swal.fire('Error', 'Validation error occurred.', 'error');
          } else {
            Swal.fire('Error', 'An unexpected error occurred.', 'error');
          }
        }
      );
    }
  }
}
