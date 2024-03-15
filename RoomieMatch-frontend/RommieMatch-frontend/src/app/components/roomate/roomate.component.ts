import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserResponse } from 'src/app/models/response/user-response';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-roomate',
  templateUrl: './roomate.component.html',
  styleUrls: ['./roomate.component.css']
})
export class RoomateComponent {
  imageUrl: string = 'assets/img/female.png';
  imageUrl1: string = 'assets/img/young.png';
  imageUrl2: string = 'assets/img/man.png';
  imageUrl3: string = 'assets/img/lines.png';
  imageUrl4: string = 'assets/img/key.png';
  imageUrl5: string = 'assets/img/dotted.png';
  imageUrl6: string = 'assets/img/phones.png';
  imageUrl7: string = 'assets/img/house.png';
  imageUrl8: string = 'assets/img/heart.png';
  imageUrl9: string = 'assets/img/phone.png';
  imageUrl10: string = 'assets/img/home1.jpg';
  imageUrl11: string = 'assets/img/home.jpg';
  roommates: UserResponse[] = [];

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

  constructor(private userService: UserService, private router: Router) { }

  viewRoommateDetails(roommateId: number) {
    console.log('Roommate ID:', roommateId);
    this.router.navigate(['/roommate-details', roommateId]);
  }
  
  ngOnInit(): void {
    this.fetchRoommates();
  }

  fetchRoommates() {
    this.userService.getAllUsers().subscribe(
      (users: UserResponse[]) => {
        this.roommates = users;
      },
      (error) => {
        console.error('Error fetching roommates:', error);
      }
    );
  }
}
