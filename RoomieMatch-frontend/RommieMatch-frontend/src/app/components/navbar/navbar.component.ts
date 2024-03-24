import { Component, OnInit } from '@angular/core';
import { NotificationResponseDTO } from 'src/app/models/response/notification-response';
import { AuthService } from 'src/app/services/auth/auth.service';
import { NotificationService } from 'src/app/services/notification/notification.service';
import { StorageService } from 'src/app/services/storage/storage.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit{
  imageUrl: string = 'assets/img/roomie.png';

  constructor(private authService : AuthService, private storageService : StorageService, private notificationService: NotificationService) {}

  user = this.storageService.getSavedUser();
  permissions = this.storageService.getPermissions();
  showAdminBoard = false;

  notificationCount: number = 0;
  notifications: NotificationResponseDTO[] = [];

  ngOnInit(): void {
    if(this.user) {
      console.log(this.user);
      
      this.showAdminBoard = this.permissions.includes('CAN_UPDATE');
      this.loadNotifications();
    }
  }

  loadNotifications(): void {
    this.notificationService.getNotificationsByRecipient().subscribe(
      (notifications: NotificationResponseDTO[]) => {
        this.notifications = notifications;
        this.notificationCount = notifications.length;
      },
      (error) => {
        console.error('Error fetching notifications:', error);
      }
    );
  }

  isLoggedIn(): boolean {
    return !!this.user;
  }

  handleLogout() {
    this.authService.logout();
  }

}
