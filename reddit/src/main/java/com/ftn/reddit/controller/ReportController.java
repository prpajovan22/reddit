package com.ftn.reddit.controller;

import com.ftn.reddit.DTO.ReportDTO;
import com.ftn.reddit.model.*;
import com.ftn.reddit.services.CommentService;
import com.ftn.reddit.services.PostService;
import com.ftn.reddit.services.ReportService;
import com.ftn.reddit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "api/report")
@CrossOrigin("*")
public class ReportController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private CommentService commentService;


    @PostMapping("/create/{post_id}")
    public ResponseEntity<String> createReport(
            @PathVariable Integer post_id,
            @RequestParam("reason") ReportReason reason) {
        try {
            LocalDate creationDate = LocalDate.now();
            Post post = postService.findById(post_id);
            Users users = userService.findById(5);

            Report report = new Report();
            report.setPost(post);
            report.setTimestamp(creationDate);
            report.setAccepted(false);
            report.setReason(reason);
            report.setByUser(users);

            reportService.createReport(report);

            return ResponseEntity.ok("Report created successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating report: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportService.getAllReportsExcludingAccepted();
        return ResponseEntity.ok(reports);
    }

    @PostMapping("/submit/{post_id}")
    public ResponseEntity<String> submitReport(
            @PathVariable Integer post_id,
            @RequestBody ReportDTO reportRequest
    ) {
        try {
            LocalDate creationDate = LocalDate.now();
            Post post = postService.findById(post_id);
            Users users = userService.findById(5);

            Report report = new Report();
            report.setPost(post);
            report.setReason(reportRequest.getReason());
            report.setTimestamp(creationDate);
            report.setAccepted(false);
            report.setByUser(users);

            reportService.createReport(report);

            return ResponseEntity.ok("Report submitted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error submitting report: " + e.getMessage());
        }
    }


    @PostMapping("/submitReport/{comment_id}")
    public ResponseEntity<String> submitCommentReport(
            @PathVariable Integer comment_id,
            @RequestBody ReportDTO reportRequest
    ) {
        try {
            LocalDate creationDate = LocalDate.now();
            Comment post = commentService.findById(comment_id);
            Users users = userService.findById(5);

            Report report = new Report();
            report.setComment(post);
            report.setReason(reportRequest.getReason());
            report.setTimestamp(creationDate);
            report.setAccepted(false);
            report.setByUser(users);

            reportService.createReport(report);

            return ResponseEntity.ok("Report submitted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error submitting report: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{report_id}")
    public ResponseEntity<String> deleteReport(@PathVariable Integer report_id) {
        try {
            reportService.deleteReport(report_id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting report: " + e.getMessage());
        }
    }

    @PutMapping("/accept/{report_id}")
    public ResponseEntity<String> acceptReport(@PathVariable Integer report_id) {
        try {
            reportService.acceptReport(report_id);
            return ResponseEntity.ok("Report accepted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error accepting report: " + e.getMessage());
        }
    }
}
