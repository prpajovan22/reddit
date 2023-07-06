package ftn.com.reddit.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "title", unique = false, nullable = false)
    private String title;

    @Column(name = "text", unique = false, nullable = false)
    private String text;

    @Column(name = "creationDate", unique = false, nullable = false)
    private LocalDate creationDate;

    @Column(name = "postPDFPath", unique = false, nullable = false)
    private String postPDFPath;

    @Column(name = "descriptionPDF", unique = false, nullable = false)
    private String descriptionPDF;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Comment> comment;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ReactionType> reaction;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ReportReason> reportReasons;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "flair_id", referencedColumnName = "flair_id")
    private Flair flair;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "community_id", referencedColumnName = "community_id")
    private Community community;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    public Post(Integer id, String title, String text, LocalDate creationDate, String postPDFPath, String descriptionPDF, Set<Comment> comment, Set<ReactionType> reaction, Set<ReportReason> reportReasons, Flair flair, Community community, Users user) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.creationDate = creationDate;
        this.postPDFPath = postPDFPath;
        this.descriptionPDF = descriptionPDF;
        this.comment = comment;
        this.reaction = reaction;
        this.reportReasons = reportReasons;
        this.flair = flair;
        this.community = community;
        this.user = user;
    }

    public Post() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getPostPDFPath() {
        return postPDFPath;
    }

    public void setPostPDFPath(String postPDFPath) {
        this.postPDFPath = postPDFPath;
    }

    public String getDescriptionPDF() {
        return descriptionPDF;
    }

    public void setDescriptionPDF(String descriptionPDF) {
        this.descriptionPDF = descriptionPDF;
    }

    public Set<Comment> getComment() {
        return comment;
    }

    public void setComment(Set<Comment> comment) {
        this.comment = comment;
    }

    public Set<ReactionType> getReaction() {
        return reaction;
    }

    public void setReaction(Set<ReactionType> reaction) {
        this.reaction = reaction;
    }

    public Set<ReportReason> getReportReasons() {
        return reportReasons;
    }

    public void setReportReasons(Set<ReportReason> reportReasons) {
        this.reportReasons = reportReasons;
    }

    public Flair getFlair() {
        return flair;
    }

    public void setFlair(Flair flair) {
        this.flair = flair;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
