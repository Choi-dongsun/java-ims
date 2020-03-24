package codesquad.domain;

import codesquad.UnAuthorizedException;
import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Issue extends AbstractEntity {
    @Size(min = 3, max = 100)
    @Column(nullable = false, length = 100)
    private String subject;

    @Lob
    @Size(min = 3)
    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    private boolean deleted = false;

    public Issue() {
    }

    public Issue(String subject, String comment, User writer) {
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
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

    public IssueDto _toIssueDto() {
        return new IssueDto(this.subject, this.comment, this.writer);
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

    @Override
    public String toString() {
        return "Issue{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", writer=" + writer +
                ", deleted=" + deleted +
                '}';
    }
}
