package codesquad.domain;

import codesquad.UnAuthorizedException;
import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Issue extends AbstractEntity {
    @NotBlank @Size(min = 3, max = 100)
    @Column(nullable = false, length = 100)
    private String subject;

    @NotEmpty @Size(min = 3)
    @Lob @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private User assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "label_id")
    private Label label;

    private boolean deleted = false;

    public Issue() {
    }

    public Issue(String subject, String comment, User writer) {
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
    }

    public Issue(String subject, String comment, User writer, Milestone milestone) {
        this(subject, comment, writer);
        this.milestone = milestone;
    }

    public Issue(Long id, String subject, String comment, User writer, Milestone milestone) {
        this(subject, comment, writer, milestone);
        if(id != null) {
            super.id = id;
        }
    }

    public String getSubject() {
        return subject;
    }

    public String getComment() {
        return comment;
    }

    public User getWriter() {
        return writer;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public User getAssignee() {
        return assignee;
    }

    public IssueDto _toIssueDto() {
        return new IssueDto(super.getId(), this.subject, this.comment, this.writer, this.milestone);
    }

    public boolean isWriter(User loginUser) {
        return writer.equals(loginUser);
    }

    public void update(User loginUser, IssueDto target) {
        if(!isWriter(loginUser)) {
            throw new UnAuthorizedException();
        }

        this.subject = target.getSubject();
        this.comment = target.getComment();
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void decideMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public void decideAssignee(User user) {
        this.assignee = user;
    }

    public void decideLabel(Label label) {
        this.label = label;
    }
}
