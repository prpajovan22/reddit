package com.ftn.reddit.model;

import com.ftn.reddit.model.Users;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", unique = true, nullable = false)
    private Integer comment_id;

    @Column(name = "text", unique = false, nullable = false)
    private String text;

    @Column(name = "timestamp", unique = false, nullable = false)
    private LocalDate timestamp;

    @Column(name = "isDeleted", unique = false, nullable = false)
    private boolean isDeleted;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id", referencedColumnName = "comment_id")
    private Comment comment;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Reaction> reactions;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Report> reports;

    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name="post_id", referencedColumnName = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private Users user;


    public Comment() {

    }

    public Comment(Integer comment_id, String text, LocalDate timestamp, boolean isDeleted, Comment comment, Set<Reaction> reactions, Set<Report> reports, Post post, Users user) {
        this.comment_id = comment_id;
        this.text = text;
        this.timestamp = timestamp;
        this.isDeleted = isDeleted;
        this.comment = comment;
        this.reactions = reactions;
        this.reports = reports;
        this.post = post;
        this.user = user;
    }

    public Integer getComment_id() {
        return comment_id;
    }

    public void setComment_id(Integer comment_id) {
        this.comment_id = comment_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Set<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(Set<Reaction> reactions) {
        this.reactions = reactions;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Users getUsers() {
        return user;
    }

    public void setUsers(Users users) {
        this.user = user;
    }
}
