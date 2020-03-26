package codesquad.domain;

import codesquad.dto.MilestoneDto;
import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Milestone extends AbstractEntity {

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    public Milestone() {
    }

    public Milestone(String subject, LocalDateTime startDate, LocalDateTime endDate) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public MilestoneDto _toMilestoneDto() {
        return new MilestoneDto(super.getId(), subject, startDate, endDate);
    }
}
