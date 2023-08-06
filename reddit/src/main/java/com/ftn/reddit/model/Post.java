package com.ftn.reddit.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", unique = true, nullable = false)
    private Integer post_id;

    @Column(name = "title", unique = false, nullable = false)
    private String title;

    @Column(name = "text", unique = false, nullable = false)
    private String text;

    @Column(name = "creationDate", unique = false, nullable = false)
    private LocalDate creationDate;

    @Column(name = "postPDFPath", unique = false)
    private String postPDFPath;

    @Column(name = "descriptionPDF", unique = false)
    private String descriptionPDF;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="community_id", referencedColumnName = "community_id")
    private Community community;

    @JsonIgnore
    @OneToMany(mappedBy="post")
    private Set<Flair> flairs;

    @JsonIgnore
    @OneToMany(mappedBy="post")
    private List<Report> reports = new ArrayList<Report>();

    @JsonIgnore
    @OneToMany(mappedBy="post")
    private List<Reaction> reactions = new ArrayList<Reaction>();

    @JsonIgnore
    @OneToMany(mappedBy="post")
    private List<Comment> comments = new ArrayList<Comment>();

    public Post(){

    }

    public Post(Integer post_id, String title, String text, LocalDate creationDate, String postPDFPath, String descriptionPDF, Users user, Community community, Set<Flair> flairs, List<Report> reports, List<Reaction> reactions, List<Comment> comments) {
        this.post_id = post_id;
        this.title = title;
        this.text = text;
        this.creationDate = creationDate;
        this.postPDFPath = postPDFPath;
        this.descriptionPDF = descriptionPDF;
        this.user = user;
        this.community = community;
        this.flairs = flairs;
        this.reports = reports;
        this.reactions = reactions;
        this.comments = comments;
    }

    public Integer getPost_id() {
        return post_id;
    }

    public void setPost_id(Integer post_id) {
        this.post_id = post_id;
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


    public Users getUsers() {
        return user;
    }

    public void setUsers(Users user) {
        this.user = user;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public Set<Flair> getFlairs() {
        return flairs;
    }

    public void setFlairs(Set<Flair> flairs) {
        this.flairs = flairs;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(List<Reaction> reactions) {
        this.reactions = reactions;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
