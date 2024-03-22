import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
import { PreferenceService } from 'src/app/services/preference/preference.service';
import { PreferenceResponse } from 'src/app/models/response/preference-response';
import { PreferenceRequest } from 'src/app/models/request/preference-request';
import { StorageService } from 'src/app/services/storage/storage.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.css']
})
export class UpdateProfileComponent implements OnInit {
  preferenceForm: FormGroup;
  preferenceId: number = 0;
  email: string = "";

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private preferenceService: PreferenceService,
    private storageService: StorageService,
  ) {
    this.preferenceForm = this.formBuilder.group({
      smoking: ['', Validators.required],
      pets: ['', Validators.required],
      visitors: ['', Validators.required],
      partying: ['', Validators.required],
      sharingBedroom: ['', Validators.required],
      hasApartment: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.preferenceId = +params['id'];
      console.log(this.preferenceId);
      
      this.fetchPreferenceDetails();
    });
  }

  fetchPreferenceDetails(): void {
    this.preferenceService.getPreferenceById(this.preferenceId).subscribe(
      (preference: PreferenceResponse) => {
        this.preferenceForm.patchValue({
          smoking: preference.smoking ? 'true' : 'false',
          pets: preference.pets ? 'true' : 'false',
          visitors: preference.visitors ? 'true' : 'false',
          partying: preference.partying ? 'true' : 'false',
          sharingBedroom: preference.sharingBedroom ? 'true' : 'false',
          hasApartment: preference.hasApartment ? 'true' : 'false'
        });
      },
      (error) => {
        console.error('Error fetching preference details:', error);
      }
    );
  }

  updatePreference(): void {
    if (this.preferenceForm.valid) {
      const updatedPreference: PreferenceRequest = {        
        userId: this.preferenceId,
        smoking: this.preferenceForm.get('smoking')?.value === 'true',
        pets: this.preferenceForm.get('pets')?.value === 'true',
        visitors: this.preferenceForm.get('visitors')?.value === 'true',
        partying: this.preferenceForm.get('partying')?.value === 'true',
        sharingBedroom: this.preferenceForm.get('sharingBedroom')?.value === 'true',
        hasApartment: this.preferenceForm.get('hasApartment')?.value === 'true'
      };
  
      const userEmail: string = this.storageService.decodeToken().sub;
        
      this.preferenceService.updatePreference(userEmail, updatedPreference).subscribe(
        (response: PreferenceResponse) => {
          Swal.fire('Success', 'Preference updated successfully!', 'success');
          this.router.navigate(['/profile']);
        },
        (error) => {
          console.error('Error updating preference:', error);
  
          if (error instanceof HttpErrorResponse && error.status === 400) {
            if (error.error && error.error.error === 'Validation error' && error.error.message) {
              Swal.fire('Error', error.error.message, 'error');
            } else {
              Swal.fire('Error', 'An error occurred while updating the preference.', 'error');
            }
          } else {
            Swal.fire('Error', 'An unexpected error occurred.', 'error');
          }
        }
      );
    }
  }
  
}
