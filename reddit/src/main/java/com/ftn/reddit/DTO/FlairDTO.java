package com.ftn.reddit.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class FlairDTO implements Serializable {

    private Integer flair_id;

    private String name;

    public FlairDTO(Integer flair_id, String name) {
        this.flair_id = flair_id;
        this.name = name;
    }

    private FlairDTO(){

    }

    public Integer getFlair_id() {
        return flair_id;
    }

    public void setFlair_id(Integer flair_id) {
        this.flair_id = flair_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
