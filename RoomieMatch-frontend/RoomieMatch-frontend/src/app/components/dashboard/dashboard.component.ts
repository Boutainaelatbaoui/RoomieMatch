import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { QuestionResponse } from 'src/app/models/response/question-response';
import { QuestionService } from 'src/app/services/questions/question.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  imageUrl5: string = 'assets/img/dotted.png';
  questions: QuestionResponse[] = [];
  pagedQuestions: QuestionResponse[] = [];
  pageSizeOptions: number[] = [3, 5, 25, 100];
  totalItems = 0;
  currentPage = 0;
  pageSize = 3;

  constructor(private questionService: QuestionService, private router: Router) {}

  ngOnInit(): void {
    this.fetchQuestions();
  }

  fetchQuestions(): void {
    this.questionService.getAllQuestions().subscribe(
      (questions) => {
        this.questions = questions;
        this.totalItems = questions.length;
        this.updatePagedQuestions();
      },
      (error) => {
        console.error('Error fetching questions:', error);
      }
    );
  }

  updatePagedQuestions(): void {
    this.pagedQuestions = this.questions.slice(
      this.currentPage * this.pageSize,
      (this.currentPage + 1) * this.pageSize
    );
  }

  onPageChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.updatePagedQuestions();
  }

  navigateToUpdateForm(id: number) {
    this.router.navigate(['/update-question', id]);
  }  

  navigateToCreateForm() {
    this.router.navigate(['/create-question']);
  }

  deleteQuestion(id: number): void {
    Swal.fire({
      title: 'Confirm Deletion',
      text: 'Are you sure you want to delete this question?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes, delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.questionService.deleteQuestion(id).subscribe(
          () => {
            Swal.fire('Deleted!', 'The question has been deleted.', 'success');
            this.fetchQuestions();
          },
          (error) => {
            console.error('Error deleting question:', error);
            Swal.fire('Error', 'Failed to delete question', 'error');
          }
        );
      }
    });
  }
}
