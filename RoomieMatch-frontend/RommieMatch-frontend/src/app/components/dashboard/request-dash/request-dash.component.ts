import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { RequestResponse } from 'src/app/models/response/request-response';
import { RequestService } from 'src/app/services/request/request.service';

@Component({
  selector: 'app-request-dash',
  templateUrl: './request-dash.component.html',
  styleUrls: ['./request-dash.component.css']
})
export class RequestDashComponent implements OnInit {
  imageUrl5: string = 'assets/img/dotted.png';
  requests: RequestResponse[] = [];
  pagedRequests: RequestResponse[] = [];
  pageSizeOptions: number[] = [7, 15, 25, 100];
  totalItems = 0;
  currentPage = 0;
  pageSize = 7;

  constructor(private requestService: RequestService, private router: Router) {}

  ngOnInit(): void {
    this.fetchRequests();
  }

  fetchRequests(): void {
    this.requestService.getAllRequests().subscribe(
      (requests) => {
        this.requests = requests;
        this.totalItems = requests.length;
        this.updatePagedRequests();
      },
      (error) => {
        console.error('Error fetching Choices:', error);
      }
    );
  }

  updatePagedRequests(): void {
    this.pagedRequests = this.requests.slice(
      this.currentPage * this.pageSize,
      (this.currentPage + 1) * this.pageSize
    );
  }

  onPageChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.updatePagedRequests();
  }

}
