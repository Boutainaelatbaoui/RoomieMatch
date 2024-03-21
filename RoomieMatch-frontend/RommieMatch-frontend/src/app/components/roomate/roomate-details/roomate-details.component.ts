import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RequestRequest } from 'src/app/models/request/request-request';
import { UserResponse } from 'src/app/models/response/user-response';
import { RequestService } from 'src/app/services/request/request.service';
import { StorageService } from 'src/app/services/storage/storage.service';
import { UserService } from 'src/app/services/user/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-roomate-details',
  templateUrl: './roomate-details.component.html',
  styleUrls: ['./roomate-details.component.css']
})
export class RoomateDetailsComponent implements OnInit {
  roommateId: number = 0;
  roommate: UserResponse = {} as UserResponse;
  email: string = "";
  isRequestAccepted: boolean = false;

  constructor(private route: ActivatedRoute, 
    private roommateService: UserService, 
    private storageService: StorageService, 
    private requestService: RequestService ) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.roommateId = +params['id'];
      this.loadRoommateDetails();
    });
  }

  fetchConnectedMemberEmail(): string | "" {
    this.email = this.storageService.decodeToken().sub;
    return this.email || "";
  }

  sendRequest(recipientEmail: string): void {
    Swal.fire({
      title: 'Confirm',
      text: 'Are you sure you want to send this request?',
      icon: 'question',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes',
      cancelButtonText: 'No'
    }).then((result) => {
      if (result.isConfirmed) {
        this.sendRequestConfirmed(recipientEmail);
      }
    });
  }

  sendRequestConfirmed(recipientEmail: string): void {
    const request: RequestRequest = {
      senderEmail: this.fetchConnectedMemberEmail(),
      recipientEmail: recipientEmail
    };

    this.requestService.sendRequest(request).subscribe(
      (response) => {
        console.log('Request sent successfully:', response);
        Swal.fire({
          icon: 'success',
          title: 'Request Sent!',
          text: 'Your request has been sent successfully.',
          confirmButtonColor: '#3085d6',
          confirmButtonText: 'OK'
        });
      },
      (error) => {
        console.error('Error sending request:', error);
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

  loadRoommateDetails() {
    this.roommateService.getRoommateDetails(this.roommateId).subscribe(
      (data: UserResponse) => {
        this.roommate = data;
        this.checkRequestStatus();
      },
      error => {
        console.error('Error fetching roommate details:', error);
      }
    );
  }

  checkRequestStatus() {
    const userEmail = this.fetchConnectedMemberEmail();
    
    if (userEmail) {
      this.requestService.getRequestStatus(userEmail, this.roommate.email).subscribe(
        (response: any) => {
          this.isRequestAccepted = response === 'ACCEPTED';
        },
        (error) => {
          console.error('Error fetching request status:', error);
        }
      );
    }
  }

  getImageUrl(gender: number): string {
    if (gender === 1) {
      return 'assets/img/man.png';
    } else{
      return 'assets/img/heart.png'; 
    }
  }
  
  occupationsMap: { [key: number]: string } = {
    1: 'Engineer',
    2: 'Doctor',
    3: 'Teacher',
    4: 'Nurse',
    5: 'Artist',
    6: 'Lawyer',
    7: 'Accountant',
    8: 'Chef',
    9: 'Police Officer',
    10: 'Firefighter',
  };

  getOccupationText(code: number): string {
    return this.occupationsMap[code] || 'Unknown';
  }

  calculateAge(birthdate: string): number {
    const birthDate = new Date(birthdate);
    const today = new Date();
    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDiff = today.getMonth() - birthDate.getMonth();
    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
      age--;
    }
    return age;
  }
}
