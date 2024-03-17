import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserResponse } from 'src/app/models/response/user-response';
import { UserService } from 'src/app/services/user/user.service';
import { StorageService } from 'src/app/services/storage/storage.service';
import { PreferenceResponse } from 'src/app/models/response/preference-response';

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
  connectedMemberId: number | null = null;
  filteredRoommates: UserResponse[] = [];
  connectedMember: UserResponse | null = null;
  minAge: number = 18;
  maxAge: number = 99;
  minBudget: number = 0;
  maxBudget: number = 10000;
  showAgeFilter: boolean = false;
  showBudgetFilter: boolean = false;

  constructor(
    private userService: UserService,
    private storageService: StorageService,
    private router: Router
  ) {}
  
  ngOnInit(): void {
    this.fetchConnectedMember();
    this.fetchRoommates();
  }

  toggleAgeFilter(): void {
    this.showAgeFilter = !this.showAgeFilter;
  }

  toggleBudgetFilter(): void {
    this.showBudgetFilter = !this.showBudgetFilter;
  }

  getImageUrl(gender: number): string {
    return gender === 1 ? 'assets/img/man.png' : 'assets/img/heart.png';
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

  viewRoommateDetails(roommateId: number) {
    this.router.navigate(['/roommate-details', roommateId]);
  }

  fetchConnectedMember(): void {
    const memberId = this.storageService.getConnectedMemberId();
    
    if (memberId) {
      this.userService.getRoommateDetails(memberId).subscribe(
        (user: UserResponse) => {
          this.connectedMember = user;
        },
        (error) => {
          console.error('Error fetching connected member details:', error);
        }
      );
    }
  }

  fetchRoommates() {
    this.userService.getAllUsers().subscribe(
      (users: UserResponse[]) => {
        this.roommates = users.filter(user => user.id !== this.connectedMember?.id);
        this.filterRoommates();
      },
      (error) => {
        console.error('Error fetching roommates:', error);
      }
    );
  }

  filterRoommates(): void {    
    this.filteredRoommates = this.roommates.filter(user => user.id !== this.connectedMember?.id);
  }
  

  filterByDesiredCity(): void {
    this.filteredRoommates = this.roommates.filter(roommate => roommate.desiredCity.name === this.connectedMember?.currentCity.name);
  }

  filterBySameAge(): void {
    this.filteredRoommates = this.roommates.filter(roommate => {
      const age = this.calculateAge(roommate.birthdate);
      return age >= this.minAge && age <= this.maxAge;
    });
  }

  filterByHasLocal(): void {
    this.filteredRoommates = this.roommates.filter(roommate => roommate.preference.hasApartment === true);
  }

  filterByGender(): void {
    this.filteredRoommates = this.roommates.filter(roommate => roommate.gender === this.connectedMember?.gender);
  }

  filterByBudget(): void {
    this.filteredRoommates = this.roommates.filter(roommate => {
      const budget = roommate.budget;
      return budget >= this.minBudget && budget <= this.maxBudget;
    });
  }

  filterBySamePreference(): void {
    const connectedPreferences = this.connectedMember?.preference;
    console.log('Connected Preferences:', connectedPreferences);

    this.filteredRoommates = this.roommates.filter(roommate => {
        if (!roommate.preference || !connectedPreferences) {
            return false;
        }

        let commonPreferencesCount = 0;

        if (roommate.preference.smoking === connectedPreferences.smoking) {
            commonPreferencesCount++;
        }
        if (roommate.preference.pets === connectedPreferences.pets) {
            commonPreferencesCount++;
        }
        if (roommate.preference.visitors === connectedPreferences.visitors) {
            commonPreferencesCount++;
        }
        if (roommate.preference.partying === connectedPreferences.partying) {
            commonPreferencesCount++;
        }
        if (roommate.preference.sharingBedroom === connectedPreferences.sharingBedroom) {
            commonPreferencesCount++;
        }
        if (roommate.preference.hasApartment === connectedPreferences.hasApartment) {
            commonPreferencesCount++;
        }

        console.log('Common Preferences Count:', commonPreferencesCount);

        return commonPreferencesCount >= 3;
    });
  }
}

