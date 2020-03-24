package codesquad.dto;

import codesquad.domain.Issue;
import codesquad.domain.User;

import javax.validation.constraints.Size;

public class IssueDto {
    @Size(min = 3, max = 100)
    private String subject;

    @Size(min = 3)
    private String comment;

    private UserDto writer;

    public IssueDto() {
    }

    public IssueDto(String subject, String comment, User writer) {
        this.subject = subject;
        this.comment = comment;
        this.writer = writer._toUserDto();
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

    public Issue _toIssue() {
        return new Issue(subject, comment, writer._toUser());
    };
}
