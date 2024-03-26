import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserResponse } from 'src/app/models/response/user-response';
import { RequestService } from 'src/app/services/request/request.service';
import { StorageService } from 'src/app/services/storage/storage.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-match',
  templateUrl: './match.component.html',
  styleUrls: ['./match.component.css']
})
export class MatchComponent implements OnInit {
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
  matches: UserResponse[] = [];

  constructor(
    private storageService: StorageService,
    private userService: UserService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.getUsersWithAcceptedRequests();
  }

  getUsersWithAcceptedRequests() : void {
    this.userService.getUsersWithAcceptedRequests().subscribe(
      (response: UserResponse[]) => {
        this.matches = response;
      },
      (error) => {
        console.error('Error fetching matches:', error);
      }
    )
  }


  getImageUrl(gender: number): string {
    return gender === 1 ? 'assets/img/man.png' : 'assets/img/heart.png';
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

  viewRoommateDetails(roommateId: number) {
    this.router.navigate(['/roommate-details', roommateId]);
  }

}
