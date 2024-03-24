import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { ChoiceResponse } from 'src/app/models/response/choice-response';
import { QuestionResponse } from 'src/app/models/response/question-response';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ChoiceService } from 'src/app/services/choice/choice.service';
import { QuestionService } from 'src/app/services/questions/question.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-choice',
  templateUrl: './choice.component.html',
  styleUrls: ['./choice.component.css']
})
export class ChoiceComponent implements OnInit {
  imageUrl5: string = 'assets/img/dotted.png';
  choices: ChoiceResponse[] = [];
  pagedChoices: ChoiceResponse[] = [];
  pageSizeOptions: number[] = [7, 15, 25, 100];
  totalItems = 0;
  currentPage = 0;
  pageSize = 7;

  constructor(private choiceService: ChoiceService, private router: Router) {}

  ngOnInit(): void {
    this.fetchChoices();
  }

  fetchChoices(): void {
    this.choiceService.getAllChoices().subscribe(
      (choices) => {
        this.choices = choices;
        this.totalItems = choices.length;
        this.updatePagedChoices();
      },
      (error) => {
        console.error('Error fetching Choices:', error);
      }
    );
  }

  updatePagedChoices(): void {
    this.pagedChoices = this.choices.slice(
      this.currentPage * this.pageSize,
      (this.currentPage + 1) * this.pageSize
    );
  }

  onPageChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.updatePagedChoices();
  }

  navigateToUpdateForm(id: number) {
    this.router.navigate(['/update-choice', id]);
  }  

  navigateToCreateForm() {
    this.router.navigate(['/create-choice']);
  }

  deleteChoice(id: number): void {
    Swal.fire({
      title: 'Confirm Deletion',
      text: 'Are you sure you want to delete this Choice?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.choiceService.deleteChoice(id).subscribe(
          () => {
            Swal.fire('Deleted!', 'The Choice has been deleted.', 'success');
            this.fetchChoices();
          },
          (error) => {
            console.error('Error deleting Choice:', error);
            Swal.fire('Error', 'Failed to delete Choice', 'error');
          }
        );
      }
    });
  }
}

