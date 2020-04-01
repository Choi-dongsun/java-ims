package codesquad.domain;

import codesquad.dto.AttachmentDto;
import support.domain.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Attachment extends AbstractEntity{

    @NotEmpty
    private String originName;

    @NotEmpty
    private String manageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User uploader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    private Issue issue;

    public Attachment() {
    }

    public Attachment(String originName, String manageName, User uploader, Issue issue) {
        this.originName = originName;
        this.manageName = manageName;
        this.uploader = uploader;
        this.issue = issue;
    }

    public AttachmentDto _toAttachmentDto() {
        return new AttachmentDto(super.id, originName, manageName);
    }
}
