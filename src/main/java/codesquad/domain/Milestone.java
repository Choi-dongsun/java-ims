package codesquad.domain;

import codesquad.dto.MilestoneDto;
import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Milestone extends AbstractEntity {

    @NotBlank
    @Column(nullable = false)
    private String subject;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime startDate;

    @NotNull @Future
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
