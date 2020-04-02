package codesquad.dto;

import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.User;

import javax.validation.constraints.Size;

public class IssueDto {

    private Long id;

    @Size(min = 3, max = 100)
    private String subject;

    @Size(min = 3)
    private String comment;

    private UserDto writer;

    private MilestoneDto milestone;

    public IssueDto() {
    }

    public IssueDto(String subject, String comment, User writer, Milestone milestone) {
        this.subject = subject;
        this.comment = comment;
        this.writer = writer._toUserDto();
        if(milestone != null) {
            this.milestone = milestone._toMilestoneDto();
        }
    }

    public IssueDto(Long id, String subject, String comment, User writer, Milestone milestone) {
        this(subject, comment, writer, milestone);
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserDto getWriter() {
        return writer;
    }

    public void setWriter(UserDto writer) {
        this.writer = writer;
    }

    public MilestoneDto getMilestone() {
        return milestone;
    }

    public void setMilestone(MilestoneDto milestone) {
        this.milestone = milestone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Issue _toIssue() {
        if(milestone == null) {
            return new Issue(id , subject, comment, writer._toUser(), null);
        }
        return new Issue(id, subject, comment, writer._toUser(), milestone._toMilestone() );
    };
}
