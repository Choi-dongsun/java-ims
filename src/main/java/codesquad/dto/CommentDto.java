package codesquad.dto;

import codesquad.domain.Comment;

public class CommentDto {

    private Long id;
    private UserDto writer;
    private String content;
    private String createdDate;
    private String modifiedDate;
    private IssueDto issue;

    public CommentDto() {
    }

    public CommentDto(String content) {
        this.content = content;
    }

    public CommentDto(long id, String content, UserDto writer, IssueDto issue, String formattedCreateDate, String formattedModifiedDate) {
        this.id = id;
        this.content = content;
        this.writer = writer;
        this.issue = issue;
        this.createdDate = formattedCreateDate;
        this.modifiedDate = formattedModifiedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getWriter() {
        return writer;
    }

    public void setWriter(UserDto writer) {
        this.writer = writer;
    }

    public IssueDto getIssue() {
        return issue;
    }

    public void setIssue(IssueDto issue) {
        this.issue = issue;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Comment _toComment() {
        return new Comment(writer._toUser(), content, issue._toIssue());
    }
}
