import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Reports } from 'src/app/models/Reports';
import { ReportService } from 'src/app/services/reportService/report.service';

@Component({
  selector: 'app-report-post',
  templateUrl: './report-post.component.html',
  styleUrls: ['./report-post.component.css']
})
export class ReportPostComponent{

  post_id: number;
  reportReason: string = 'BREAKS_RULES'; // Initialize with a default value

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private reportsService: ReportService
  ) {
    this.post_id = +this.route.snapshot.paramMap.get('post_id'); 
  }

  submitReport() {
    if (this.reportReason) {
      const reportData = {
        reason: this.reportReason,
      };

      this.reportsService.createReport(this.post_id, reportData).subscribe(
        (response) => {
          console.log('Report submitted successfully');
          this.redirectToHome();
        },
        (error) => {
          console.error('Error submitting report:', error);
        }
      );
    } else {
      console.error('Please select a reason');
    }
  }

  redirectToHome(){
    this.router.navigate(['/home']);
  }
}