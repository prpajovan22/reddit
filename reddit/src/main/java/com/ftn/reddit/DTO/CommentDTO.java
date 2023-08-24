package com.ftn.reddit.DTO;

import com.ftn.reddit.model.Comment;
import com.ftn.reddit.model.Reaction;
import com.ftn.reddit.model.ReactionType;
import com.ftn.reddit.model.ReportReason;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class CommentDTO implements Serializable {

    private Integer comment_id;

    private String text;

    private LocalDate timeStamp;

    private boolean isDeleted;

    private ReactionType reactionType;

    private CommentDTO commentDTO;

    private PostDTO postDTO;

    private UsersDTO usersDTO;

    private ReportReason reportReason;

    private int netReactions;

    public CommentDTO(Integer comment_id, String text, LocalDate timeStamp, boolean isDeleted, ReactionType reactionType,
                      CommentDTO commentDTO, PostDTO postDTO, UsersDTO usersDTO, ReportReason reportReason, int netReactions) {
        this.comment_id = comment_id;
        this.text = text;
        this.timeStamp = timeStamp;
        this.isDeleted = isDeleted;
        this.reactionType = reactionType;
        this.commentDTO = commentDTO;
        this.postDTO = postDTO;
        this.usersDTO = usersDTO;
        this.reportReason = reportReason;
        this.netReactions = netReactions;
    }

    public CommentDTO() {

    }

    public CommentDTO(Comment comment){
        this.comment_id = comment.getComment_id();
        this.text = comment.getText();
        this.timeStamp = comment.getTimestamp();
        this.isDeleted = comment.isDeleted();
        this.commentDTO = this;
        this.usersDTO = new UsersDTO(comment.getUser());
        this.postDTO = new PostDTO(comment.getPost());
        this.netReactions = calculateNetReactions((List<Reaction>) comment.getReactions());
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

    public LocalDate getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDate timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public ReactionType getReactionType() {
        return reactionType;
    }

    public void setReactionType(ReactionType reactionType) {
        this.reactionType = reactionType;
    }

    public CommentDTO getCommentDTO() {
        return commentDTO;
    }

    public void setCommentDTO(CommentDTO commentDTO) {
        this.commentDTO = commentDTO;
    }

    public PostDTO getPostDTO() {
        return postDTO;
    }

    public void setPostDTO(PostDTO postDTO) {
        this.postDTO = postDTO;
    }

    public UsersDTO getUsersDTO() {
        return usersDTO;
    }

    public void setUsersDTO(UsersDTO usersDTO) {
        this.usersDTO = usersDTO;
    }

    public ReportReason getReportReason() {
        return reportReason;
    }

    public void setReportReason(ReportReason reportReason) {
        this.reportReason = reportReason;
    }

    public int getNetReactions() {
        return netReactions;
    }

    public void setNetReactions(int netReactions) {
        this.netReactions = netReactions;
    }

    // Helper method to calculate net reactions
    private int calculateNetReactions(List<Reaction> reactions) {
        int netReactions = 0;

        for (Reaction reaction : reactions) {
            if (reaction.getType() == ReactionType.UPWOTE) {
                netReactions++;
            } else if (reaction.getType() == ReactionType.DOWNWOTE) {
                netReactions--;
            }
        }

        return netReactions;
    }
}
