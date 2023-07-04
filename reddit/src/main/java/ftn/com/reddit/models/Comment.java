package ftn.com.reddit.models;

import lombok.Data;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "reaction", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ReactionType> reaction;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Comment> replies;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ReportReason> report;

    public Comment(Integer comment_id, String text, LocalDate timestamp, boolean isDeleted, Set<ReactionType> reaction, Set<Comment> replies, Post post, Users user, Set<ReportReason> report) {
        this.comment_id = comment_id;
        this.text = text;
        this.timestamp = timestamp;
        this.isDeleted = isDeleted;
        this.reaction = reaction;
        this.replies = replies;
        this.post = post;
        this.user = user;
        this.report = report;
    }

    public Comment() {
    }

    public Integer getId() {
        return comment_id;
    }

    public void setId(Integer id) {
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

    public Set<ReactionType> getReaction() {
        return reaction;
    }

    public void setReaction(Set<ReactionType> reaction) {
        this.reaction = reaction;
    }

    public Set<Comment> getReplies() {
        return replies;
    }

    public void setReplies(Set<Comment> replies) {
        this.replies = replies;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Set<ReportReason> getReport() {
        return report;
    }

    public void setReport(Set<ReportReason> report) {
        this.report = report;
    }
}
