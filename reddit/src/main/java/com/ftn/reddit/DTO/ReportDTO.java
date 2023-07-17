package com.ftn.reddit.DTO;

import com.ftn.reddit.model.ReportReason;

import java.time.LocalDate;

public class ReportDTO {

    private Integer report_id;
    private ReportReason reason;
    private LocalDate timestamp;
    private boolean accepted;
}
