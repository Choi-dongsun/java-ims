package codesquad.domain;

import codesquad.dto.CommentDto;
import support.domain.AbstractEntity;

import javax.persistence.*;

@Entity
public class Comment extends AbstractEntity {

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    private Issue issue;

    private boolean deleted = false;

    public Comment() {
    }

    public Comment(User writer, String content, Issue issue) {
        this.writer = writer;
        this.content = content;
        this.issue = issue;
    }

    public CommentDto _toCommentDto() {
        return new CommentDto(
                super.getId(), content, writer._toUserDto(), issue._toIssueDto(),
                super.getFormattedCreateDate(), super.getFormattedModifiedDate()
        );
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isWriter(User user) {
        return writer.equals(user);
    }

    public void delete() {
        deleted = true;
    }

    public Comment update(String content) {
        this.content = content;
        return this;
    }
}
