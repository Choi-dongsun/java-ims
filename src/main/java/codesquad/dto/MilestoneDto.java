package codesquad.dto;

import codesquad.domain.Milestone;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MilestoneDto {

    private Long id;
    private String subject;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public MilestoneDto() {
    }

    public MilestoneDto(Long id, String subject, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = LocalDateTime.parse(startDate);
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = LocalDateTime.parse(endDate);
    }

    public String getFormattedDueDate() {
        return endDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
    }

    public Milestone _toMilestone() {
        return new Milestone(subject, startDate, endDate);
    }
}
