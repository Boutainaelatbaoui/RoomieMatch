import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  imageUrl: string = 'assets/img/roomie.png';
  imageUrl1: string = 'assets/img/female.png';

  constructor(private authService: AuthService) {}

  handleLogout() {
    this.authService.logout();
  }
}
