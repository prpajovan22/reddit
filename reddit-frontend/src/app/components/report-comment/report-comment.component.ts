import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ReportService } from 'src/app/services/reportService/report.service';

@Component({
  selector: 'app-report-comment',
  templateUrl: './report-comment.component.html',
  styleUrls: ['./report-comment.component.css']
})
export class ReportCommentComponent {

  comment_id: number;
  reportReason: string = 'BREAKS_RULES'; 

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private reportsService: ReportService
  ) {
    this.comment_id = +this.route.snapshot.paramMap.get('comment_id'); 
  }

  submitReport() {
    if (this.reportReason) {
      const reportData = {
        reason: this.reportReason,
      };

      this.reportsService.createReportComment(this.comment_id, reportData).subscribe(
        (response) => {
          console.log('Report submitted successfully');
          this.redirectToCommunitys();
        },
        (error) => {
          console.error('Error submitting report:', error);
          this.redirectToCommunitys();
        }
      );
    } else {
      console.error('Please select a reason');
    }
  }

  redirectToCommunitys() {
    this.router.navigate(['/home']);
}
}
