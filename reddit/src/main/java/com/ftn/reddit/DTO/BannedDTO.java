package com.ftn.reddit.DTO;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class BannedDTO implements Serializable {

    private Integer id;

    private LocalDate timestamp;

    private UsersDTO usersDTO;

    private CommunityDTO communityDTO;

    public BannedDTO() {

    }

    public BannedDTO(Integer id, LocalDate timestamp, UsersDTO usersDTO, CommunityDTO communityDTO) {
        this.id = id;
        this.timestamp = timestamp;
        this.usersDTO = usersDTO;
        this.communityDTO = communityDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public UsersDTO getUsersDTO() {
        return usersDTO;
    }

    public void setUsersDTO(UsersDTO usersDTO) {
        this.usersDTO = usersDTO;
    }

    public CommunityDTO getCommunityDTO() {
        return communityDTO;
    }

    public void setCommunityDTO(CommunityDTO communityDTO) {
        this.communityDTO = communityDTO;
    }
}
