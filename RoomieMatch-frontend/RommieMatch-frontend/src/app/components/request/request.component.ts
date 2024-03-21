import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RequestResponse } from 'src/app/models/response/request-response';
import { RequestService } from 'src/app/services/request/request.service';
import { StorageService } from 'src/app/services/storage/storage.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-request',
  templateUrl: './request.component.html',
  styleUrls: ['./request.component.css']
})
export class RequestComponent implements OnInit {
  imageUrl5: string = 'assets/img/dotted.png';
  imageUrl: string = 'assets/img/female.png';
  imageUrl1: string = 'assets/img/young.png';
  imageUrl2: string = 'assets/img/man.png';
  imageUrl3: string = 'assets/img/lines.png';
  imageUrl4: string = 'assets/img/key.png';
  imageUrl6: string = 'assets/img/phones.png';
  imageUrl7: string = 'assets/img/house.png';
  imageUrl8: string = 'assets/img/heart.png';
  imageUrl9: string = 'assets/img/phone.png';
  imageUrl10: string = 'assets/img/home1.jpg';
  imageUrl11: string = 'assets/img/home.jpg';
  email: string = "";
  receivedRequests: RequestResponse[] = [];
  sentRequests: RequestResponse[] = [];
  showReceivedRequests: boolean = true;

  constructor(
    private storageService: StorageService,
    private requestService: RequestService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.fetchReceivedRequests();
    this.fetchSentRequests();
  }

  fetchConnectedMemberEmail(): string | "" {
    this.email = this.storageService.decodeToken().sub;
    console.log('Email:', this.email);
    
    return this.email || "";
  }

  fetchReceivedRequests(): void {
    const userEmail = this.fetchConnectedMemberEmail();
    if (userEmail) {
      this.requestService.getReceivedRequests(userEmail).subscribe(
        (requests: RequestResponse[]) => {
          this.receivedRequests = requests.filter(request => request.status === "PENDING");
          console.log('Received requests:', this.receivedRequests);
          
        },
        (error) => {
          console.error('Error fetching received requests:', error);
        }
      );
    }
  }

  fetchSentRequests(): void {
    const userEmail = this.fetchConnectedMemberEmail();
    if (userEmail) {
      this.requestService.getSentRequests(userEmail).subscribe(
        (requests: RequestResponse[]) => {
          this.sentRequests = requests;
        },
        (error) => {
          console.error('Error fetching sent requests:', error);
        }
      );
    }
  }

  acceptRequest(requestId: number, userEmail: string): void {
    this.requestService.acceptRequest(requestId, userEmail).subscribe(
      (response: RequestResponse) => {
        this.fetchReceivedRequests();
        Swal.fire('Request Accepted', 'The request has been accepted successfully.', 'success');
      },
      (error) => {
        console.error('Error accepting request:', error);
        Swal.fire('Error', 'Failed to accept the request. Please try again later.', 'error');
      }
    );
  }

  rejectRequest(requestId: number, userEmail: string): void {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You are about to reject this request. This action cannot be undone.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, reject it!',
      cancelButtonText: 'Cancel'
    }).then((result) => {
      if (result.isConfirmed) {
        this.requestService.rejectRequest(requestId, userEmail).subscribe(
          (response: RequestResponse) => {
            this.fetchReceivedRequests();
            Swal.fire('Request Rejected', 'The request has been rejected successfully.', 'success');
          },
          (error) => {
            console.error('Error rejecting request:', error);
            Swal.fire('Error', 'Failed to reject the request. Please try again later.', 'error');
          }
        );
      } else if (result.dismiss === Swal.DismissReason.cancel) {
        Swal.fire('Cancelled', 'The request rejection has been cancelled.', 'info');
      }
    });
  }

  toggleShowReceivedRequests(): void {
    this.showReceivedRequests = true;
  }

  toggleShowSentRequests(): void {
    this.showReceivedRequests = false;
  }

  getImageUrl(gender: number): string {
    return gender === 1 ? 'assets/img/man.png' : 'assets/img/heart.png';
  }

  viewRoommateDetails(roommateId: number) {
    this.router.navigate(['/roommate-details', roommateId]);
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
