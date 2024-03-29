package com.ftn.reddit.DTO;

import com.ftn.reddit.model.Post;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class PostDTO implements Serializable {

    private Integer post_id;

    private String title;

    private String text;

    private LocalDate creationDate;

    private String postPDFPath;

    private String descriptionPDF;

    private FlairDTO flairDTO;

    private CommunityDTO community;

    private Integer community_id;

    private UsersDTO user;

    private int netReactions;

    private double averageRection;

    public PostDTO() {

    }

    public PostDTO(Integer post_id, String title, String text, LocalDate creationDate,
                   String postPDFPath, String descriptionPDF, FlairDTO flairDTO, CommunityDTO community,
                   UsersDTO user, Integer community_id,int netReactions) {
        this.post_id = post_id;
        this.title = title;
        this.text = text;
        this.creationDate = creationDate;
        this.postPDFPath = postPDFPath;
        this.descriptionPDF = descriptionPDF;
        this.flairDTO = flairDTO;
        this.community = community;
        this.user = user;
        this.community_id = community_id;
        this.netReactions = netReactions;
    }

    public PostDTO(Post post) {
        this.post_id = post.getPost_id();
        this.title = post.getTitle();
        this.text = post.getText();
        this.creationDate = post.getCreationDate();
        this.postPDFPath = post.getPostPDFPath();
        this.descriptionPDF = post.getDescriptionPDF();
    }


    public PostDTO(Post post, int netReaction) {
        this.post_id = post.getPost_id();
        this.title = post.getTitle();
        this.text = post.getText();
        this.creationDate = post.getCreationDate();
        this.netReactions = netReaction;
        this.community_id = post.getCommunity().getCommunity_id();
        this.descriptionPDF = post.getDescriptionPDF();
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

    public Integer getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(Integer community_id) {
        this.community_id = community_id;
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

    public FlairDTO getFlairDTO() {
        return flairDTO;
    }

    public void setFlairDTO(FlairDTO flairDTO) {
        this.flairDTO = flairDTO;
    }

    public CommunityDTO getCommunity() {
        return community;
    }

    public void setCommunity(CommunityDTO community) {
        this.community = community;
    }

    public UsersDTO getUser() {
        return user;
    }

    public void setUser(UsersDTO user) {
        this.user = user;
    }

    public int getNetReactions() {
        return netReactions;
    }

    public void setNetReactions(int netReactions) {
        this.netReactions = netReactions;
    }
}
