import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Reports } from 'src/app/models/Reports';
import { ReportService } from 'src/app/services/reportService/report.service';

@Component({
  selector: 'app-all-reports',
  templateUrl: './all-reports.component.html',
  styleUrls: ['./all-reports.component.css']
})
export class AllReportsComponent implements OnInit {

  reports:Reports[] = [];
  report:any;

  constructor(private userService: ReportService, private router: Router) { }

  ngOnInit(): void {
    this.userService.getReports().subscribe((reports) => this.reports = reports)
  }

  deleteReport(report_id: number): void {
    if (confirm('Are you sure you want to delete this report?')) {
        this.userService.deleteReport(report_id).subscribe(
            (response) => {
                if (response.status === 204) {
                    console.log('Report deleted successfully');
                    this.reports = this.reports.filter((report) => report.report_id !== report_id);
                } else {
                    console.error('Error deleting report. Unexpected response:', response);
                }
            },
            (error) => {
                console.error('Error deleting report:', error);
            }
        );
    }
}

acceptReport(report_id: number): void {
  if (confirm('Are you sure you want to accept this report?')) {
    this.userService.acceptReport(report_id).subscribe(
      (response) => {
        console.log('Report accepted successfully');
        this.reports = this.reports.filter((report) => report.report_id !== report_id);
        
        window.location.reload();
      },
      (error) => {
        console.error('Error accepting report:', error);
      }
    );
  }
}
}
