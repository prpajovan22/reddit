package com.ftn.reddit.model.pretraga;

public class CommunitySearchCriteria {

    private String name;
    private String description;

    private Long fromPostCount;
    private Long toPostCount;
    private Long fromReactionCount;
    private Long toReactionCount;

    public CommunitySearchCriteria(String name, String description, Long fromPostCount, Long toPostCount, Long fromReactionCount, Long toReactionCount) {
        this.name = name;
        this.description = description;
        this.fromPostCount = fromPostCount;
        this.toPostCount = toPostCount;
        this.fromReactionCount = fromReactionCount;
        this.toReactionCount = toReactionCount;
    }

    public CommunitySearchCriteria(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getFromPostCount() {
        return fromPostCount;
    }

    public void setFromPostCount(Long fromPostCount) {
        this.fromPostCount = fromPostCount;
    }

    public Long getToPostCount() {
        return toPostCount;
    }

    public void setToPostCount(Long toPostCount) {
        this.toPostCount = toPostCount;
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
}
