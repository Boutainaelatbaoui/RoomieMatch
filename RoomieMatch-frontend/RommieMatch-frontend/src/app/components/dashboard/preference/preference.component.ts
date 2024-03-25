import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { PreferencePaged } from 'src/app/models/response/preference-paged';
import { PreferenceResponse } from 'src/app/models/response/preference-response';
import { PreferenceService } from 'src/app/services/preference/preference.service';

@Component({
  selector: 'app-preference',
  templateUrl: './preference.component.html',
  styleUrls: ['./preference.component.css']
})
export class PreferenceComponent implements OnInit {
  imageUrl5: string = 'assets/img/dotted.png';
  preferences: PreferenceResponse[] = [];
  pagedPreferences: PreferenceResponse[] = [];
  pageSizeOptions: number[] = [7, 15, 25, 100];
  totalItems = 0;
  currentPage = 0;
  pageSize = 7;

  constructor(private preferenceService: PreferenceService) {}

  ngOnInit(): void {
    this.fetchPreferencesPaged();
  }

  fetchPreferencesPaged(): void {
    this.preferenceService.getAllPagePreferences(this.currentPage, this.pageSize).subscribe(
      (preferences) => {
        this.preferences = preferences.content;
        this.totalItems = preferences.totalElements;
        this.updatePagedPreferences();
      },
      (error) => {
        console.error('Error fetching Preference:', error);
      }
    );
  }

  updatePagedPreferences(): void {
    const startIndex = this.currentPage * this.pageSize;
    this.pagedPreferences = this.preferences.slice(startIndex, startIndex + this.pageSize);
  }

  onPageChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.fetchPreferencesPaged();
  }
}
