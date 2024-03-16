import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { UserResponse } from 'src/app/models/response/user-response';
import { Role } from 'src/app/models/role';
import { StorageService } from 'src/app/services/storage/storage.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-member',
  templateUrl: './member.component.html',
  styleUrls: ['./member.component.css']
})
export class MemberComponent implements OnInit {
  pagedUsers: UserResponse[] = [];
  roles: Role[] = [];
  selectedRoleIds: { [memberId: number]: any | null } = {};
  pageSizeOptions: number[] = [7, 15, 25, 100];
  totalItems = 0;
  currentPage = 0;
  pageSize = 7;

  constructor(private userService: UserService, private router: Router, private storageService: StorageService) {}

  user = this.storageService.getSavedUser();
  permissions = this.storageService.getPermissions();
  showAdminBoard = false;
  showManagerBoard = false;

  ngOnInit(): void {
    if(this.user) {
      console.log(this.user);
      
      this.showAdminBoard = this.permissions.includes('CAN_MANAGE_COMPETITIONS');
      this.showManagerBoard = this.permissions.includes('CAN_MANAGE_USERS');
    }
    this.loadRoles();
    this.fetchUsers();
    this.initializeSelectedRoleIds();
  }

  fetchUsers(): void {
    this.userService.getAllUsers().subscribe(
      (users) => {
        this.totalItems = users.length;
        this.updatePagedUsers(users);
      },
      (error) => {
        console.error('Error fetching users:', error);
      }
    );
  }

  updatePagedUsers(users: UserResponse[]): void {
    this.pagedUsers = users.slice(
      this.currentPage * this.pageSize,
      (this.currentPage + 1) * this.pageSize
    );
  }

  onPageChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.fetchUsers();
  }
}
