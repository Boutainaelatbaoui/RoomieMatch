import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserResponse } from 'src/app/models/response/user-response';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-roomate-details',
  templateUrl: './roomate-details.component.html',
  styleUrls: ['./roomate-details.component.css']
})
export class RoomateDetailsComponent {
  roommateId: number = 0;
  roommate: UserResponse = {} as UserResponse;

  constructor(private route: ActivatedRoute, private roommateService: UserService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.roommateId = +params['id'];
      console.log(this.roommateId);
      
      this.loadRoommateDetails();
    });
  }

  loadRoommateDetails() {
    this.roommateService.getRoommateDetails(this.roommateId).subscribe(
      (data: UserResponse) => {
        this.roommate = data;
      },
      error => {
        console.error('Error fetching roommate details:', error);
      }
    );
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
