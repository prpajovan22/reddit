package com.ftn.reddit.DTO;

import com.ftn.reddit.model.UserRole;
import com.ftn.reddit.model.Users;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UsersDTO {

    private Integer user_id;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private LocalDate registrationDate;
    private String description;
    private String displayName;
    private UserRole userRole;
    private PostDTO postDTO;
    private BannedDTO bannedDTO;
    private FlairDTO flairDTO;
    private CommentDTO commentDTO;
    private ReportDTO reportDTO;
    private String token;

    public UsersDTO(Integer user_id, String username, String password, String email, String avatar, LocalDate registrationDate, String description, String displayName, UserRole userRole, PostDTO postDTO, BannedDTO bannedDTO, FlairDTO flairDTO, CommentDTO commentDTO, ReportDTO reportDTO, String token) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.registrationDate = registrationDate;
        this.description = description;
        this.displayName = displayName;
        this.userRole = userRole;
        this.postDTO = postDTO;
        this.bannedDTO = bannedDTO;
        this.flairDTO = flairDTO;
        this.commentDTO = commentDTO;
        this.reportDTO = reportDTO;
        this.token = token;
    }

    public UsersDTO(Users users) {
        this.user_id = users.getUser_id();
    }

    public UsersDTO(){}

    public Users ToUsersEntity(){
        Users users = new Users();
        users.setUser_id(this.user_id);
        users.setUsername(this.username);
        users.setPassword(this.password);
        users.setEmail(this.email);
        users.setDescription(this.description);
        users.setDisplayName(this.displayName);
        users.setRegistrationDate(this.registrationDate);
        users.setUserRole(this.userRole);
        return users;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public PostDTO getPostDTO() {
        return postDTO;
    }

    public void setPostDTO(PostDTO postDTO) {
        this.postDTO = postDTO;
    }

    public BannedDTO getBannedDTO() {
        return bannedDTO;
    }

    public void setBannedDTO(BannedDTO bannedDTO) {
        this.bannedDTO = bannedDTO;
    }

    public FlairDTO getFlairDTO() {
        return flairDTO;
    }

    public void setFlairDTO(FlairDTO flairDTO) {
        this.flairDTO = flairDTO;
    }

    public CommentDTO getCommentDTO() {
        return commentDTO;
    }

    public void setCommentDTO(CommentDTO commentDTO) {
        this.commentDTO = commentDTO;
    }

    public ReportDTO getReportDTO() {
        return reportDTO;
    }

    public void setReportDTO(ReportDTO reportDTO) {
        this.reportDTO = reportDTO;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
