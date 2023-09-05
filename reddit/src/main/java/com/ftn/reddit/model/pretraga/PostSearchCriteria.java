package com.ftn.reddit.model.pretraga;

public class PostSearchCriteria {

    String text;

    String title;

    Long fromReactionCount;

    Long toReactionCount;

    Long fromCommentCount;

    Long toCommentCount;

    Integer community_id;

    public PostSearchCriteria(String text, String title, Long fromReactionCount, Long toReactionCount, Long fromCommentCount, Long toCommentCount, Integer community_id) {
        this.text = text;
        this.title = title;
        this.fromReactionCount = fromReactionCount;
        this.toReactionCount = toReactionCount;
        this.fromCommentCount = fromCommentCount;
        this.toCommentCount = toCommentCount;
        this.community_id = community_id;
    }

    public  PostSearchCriteria(){}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getFromReactionCount() {
        return fromReactionCount;
    }

    public void setFromReactionCount(Long fromReactionCount) {
        this.fromReactionCount = fromReactionCount;
    }

    public Long getToReactionCount() {
        return toReactionCount;
    }

    public void setToReactionCount(Long toReactionCount) {
        this.toReactionCount = toReactionCount;
    }

    public Integer getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(Integer community_id) {
        this.community_id = community_id;
    }

    public Long getFromCommentCount() {
        return fromCommentCount;
    }

    public void setFromCommentCount(Long fromCommentCount) {
        this.fromCommentCount = fromCommentCount;
    }

    public Long getToCommentCount() {
        return toCommentCount;
    }

    public void setToCommentCount(Long toCommentCount) {
        this.toCommentCount = toCommentCount;
    }
}
