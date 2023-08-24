package com.ftn.reddit.model.pretraga;

public class PostSearchCriteria {

    String text;

    String title;

    Long fromReactionCount;

    Long toReactionCount;

    Integer community_id;

    public PostSearchCriteria(String text, String title, Long fromReactionCount, Long toReactionCount,Integer community_id) {
        this.text = text;
        this.title = title;
        this.fromReactionCount = fromReactionCount;
        this.toReactionCount = toReactionCount;
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
}
