package com.ftn.reddit.DTO;

import com.ftn.reddit.model.ReportReason;

import java.time.LocalDate;

public class ReportDTO {

    private Integer report_id;
    private ReportReason reason;
    private LocalDate timestamp;
    private boolean accepted;

    private UsersDTO usersDTO;

    public ReportDTO(Integer report_id, ReportReason reason, LocalDate timestamp, boolean accepted,UsersDTO usersDTO) {
        this.report_id = report_id;
        this.reason = reason;
        this.timestamp = timestamp;
        this.accepted = accepted;
        this.usersDTO = usersDTO;
    }

    public ReportDTO(){}

    public Integer getReport_id() {
        return report_id;
    }

    public void setReport_id(Integer report_id) {
        this.report_id = report_id;
    }

    public ReportReason getReason() {
        return reason;
    }

    public void setReason(ReportReason reason) {
        this.reason = reason;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public UsersDTO getUsersDTO() {
        return usersDTO;
    }

    public void setUsersDTO(UsersDTO usersDTO) {
        this.usersDTO = usersDTO;
    }
}
