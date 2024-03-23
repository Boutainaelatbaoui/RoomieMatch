import { Component } from '@angular/core';

@Component({
  selector: 'app-update-info',
  templateUrl: './update-info.component.html',
  styleUrls: ['./update-info.component.css']
})
export class UpdateInfoComponent {
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
}
