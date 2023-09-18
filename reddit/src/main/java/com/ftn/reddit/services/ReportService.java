package com.ftn.reddit.services;

import com.ftn.reddit.model.Post;
import com.ftn.reddit.model.Report;
import com.ftn.reddit.repositorys.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public void createReport(Report report) {
        reportRepository.save(report);
    }

    public List<Report> findAll(){return reportRepository.findAll();}

    public List<Report> getAllReportsExcludingAccepted() {
        return reportRepository.findByAccepted(false);
    }
    public boolean hasAcceptedReportForPost(Post post) {
        return reportRepository.existsByPostAndAccepted(post, true);
    }

    public List<Report> getAllAcceptedReports() {
        return reportRepository.findByAccepted(true);
    }

    public void deleteReport(Integer report_id) {
        reportRepository.deleteById(report_id);
    }

    public void acceptReport(Integer report_id) {
        Report report = reportRepository.findById(report_id)
                .orElseThrow(NoSuchElementException::new);

        report.setAccepted(true);

        reportRepository.save(report);
    }
}
