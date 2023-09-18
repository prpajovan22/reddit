import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Reports } from 'src/app/models/Reports';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  private apiReportUrl = `${environment.apiURL}/api/report`;

  constructor(private http: HttpClient) {}

  createReport(post_id: number, reportData: any) {
    const url = `${this.apiReportUrl}/submit/${post_id}`;

    return this.http.post(url, reportData);
  }

  createReportComment(comment_id: number, reportData: any) {
    const url = `${this.apiReportUrl}/submitReport/${comment_id}`;

    return this.http.post(url, reportData);
  }

  getReports(): Observable<Reports[]>{
    return this.http.get<Reports[]>(`${this.apiReportUrl}/all`,);
  }

  deleteReport(report_id: number): Observable<any> {
    const url = `${this.apiReportUrl}/delete/${report_id}`;
    return this.http.delete(url, { observe: 'response' });
}

acceptReport(report_id: number): Observable<any> {
  const url = `${this.apiReportUrl}/accept/${report_id}`;
  return this.http.put(url, null);
}

}
