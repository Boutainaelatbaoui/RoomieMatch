import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { CityResponse } from 'src/app/models/response/city-response';
import { CityService } from 'src/app/services/cities/city.service';

@Component({
  selector: 'app-city',
  templateUrl: './city.component.html',
  styleUrls: ['./city.component.css']
})
export class CityComponent implements OnInit {
  imageUrl5: string = 'assets/img/dotted.png';
  cities: CityResponse[] = [];
  pagedCities: CityResponse[] = [];
  pageSizeOptions: number[] = [7, 15, 25, 100];
  totalItems = 0;
  currentPage = 0;
  pageSize = 7;

  constructor(private cityService: CityService, private router: Router) {}

  ngOnInit(): void {
    this.fetchCities();
  }

  fetchCities(): void {
    this.cityService.getAllCities().subscribe(
      (cities) => {
        this.cities = cities;
        this.totalItems = cities.length;
        this.updatePagedRequests();
      },
      (error) => {
        console.error('Error fetching Choices:', error);
      }
    );
  }

  updatePagedRequests(): void {
    this.pagedCities = this.cities.slice(
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
