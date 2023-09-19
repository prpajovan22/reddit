package com.ftn.reddit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id", unique = true, nullable = false)
    private Integer report_id;

    @Column(name = "reason", unique = false, nullable = false)
    private ReportReason reason;

    @Column(name = "timestamp", unique = false, nullable = false)
    private LocalDate timestamp;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private Users byUser;

    @Column(name = "accepted", unique = false, nullable = false)
    private boolean accepted;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comment_id", referencedColumnName = "comment_id")
    private Comment comment;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;

    public Report() {

    }

    public Report(Integer report_id, ReportReason reason, LocalDate timestamp, Users byUser, boolean accepted, Comment comment, Post post) {
        this.report_id = report_id;
        this.reason = reason;
        this.timestamp = timestamp;
        this.byUser = byUser;
        this.accepted = accepted;
        this.comment = comment;
        this.post = post;
    }

    public Integer getReport_id() {
        return report_id;
    }

    public void setReport_id(Integer report_id) {
        this.report_id = report_id;
    }

    public ReportReason getReason() {
        return reason;
    }

    public void setReason(ReportReason reason) {
        this.reason = reason;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public Users getByUser() {
        return byUser;
    }

    public void setByUser(Users byUser) {
        this.byUser = byUser;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
