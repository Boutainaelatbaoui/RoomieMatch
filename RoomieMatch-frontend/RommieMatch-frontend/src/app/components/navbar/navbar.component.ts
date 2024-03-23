import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';
import { StorageService } from 'src/app/services/storage/storage.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit{
  imageUrl: string = 'assets/img/roomie.png';

  constructor(private authService : AuthService, private storageService : StorageService) {}

  user = this.storageService.getSavedUser();
  permissions = this.storageService.getPermissions();
  showAdminBoard = false;

  notificationCount: number = 5;
  notifications: any[] = [
    { message: 'Notification 1' },
    { message: 'Notification 2' },
    { message: 'Notification 3' },
    { message: 'Notification 4' },
    { message: 'Notification 5' }
  ];

  ngOnInit(): void {
    if(this.user) {
      console.log(this.user);
      
      this.showAdminBoard = this.permissions.includes('CAN_UPDATE');
    }
  }

  isLoggedIn(): boolean {
    return !!this.user;
  }

  handleLogout() {
    this.authService.logout();
  }

}
