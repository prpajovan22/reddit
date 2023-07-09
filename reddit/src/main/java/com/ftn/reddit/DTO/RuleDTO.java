package com.ftn.reddit.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class RuleDTO implements Serializable {

    private Integer rule_id;

    private String description;

    private CommunityDTO communityDTO;

    public RuleDTO(Integer rule_id, String description, CommunityDTO communityDTO) {
        this.rule_id = rule_id;
        this.description = description;
        this.communityDTO = communityDTO;
    }

    public RuleDTO() {

    }

    public Integer getRule_id() {
        return rule_id;
    }

    public void setRule_id(Integer rule_id) {
        this.rule_id = rule_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CommunityDTO getCommunityDTO() {
        return communityDTO;
    }

    public void setCommunityDTO(CommunityDTO communityDTO) {
        this.communityDTO = communityDTO;
    }
}
