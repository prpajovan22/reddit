package com.ftn.reddit.DTO;

import com.ftn.reddit.model.Community;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class CommunityDTO implements Serializable {

    private Integer community_id;

    private String name;

    private String description;

    private LocalDate creationDate;

    private boolean isSuspended;

    private String suspendedReason;

    public CommunityDTO(Integer community_id, String name, String description, LocalDate creationDate, boolean isSuspended, String suspendedReason) {
        this.community_id = community_id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.isSuspended = isSuspended;
        this.suspendedReason = suspendedReason;
    }

    public CommunityDTO() {
    }

    public CommunityDTO(Community community) {
        this.community_id = community.getCommunity_id();
        this.name = community.getName();
        this.description = community.getDescription();
        this.creationDate = community.getCreationDate();
        this.isSuspended = community.isSuspended();
        this.suspendedReason = community.getSuspendedReason();

    }

    public Integer getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(Integer community_id) {
        this.community_id = community_id;
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    public String getSuspendedReason() {
        return suspendedReason;
    }

    public void setSuspendedReason(String suspendedReason) {
        this.suspendedReason = suspendedReason;
    }
}
