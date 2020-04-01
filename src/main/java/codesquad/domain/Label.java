package codesquad.domain;

import codesquad.dto.LabelDto;
import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Entity
public class Label extends AbstractEntity {

    @NotBlank
    @Column(nullable = false)
    private String subject;

    public Label() {
    }

    public Label(String subject) {
        this.subject = subject;
    }

    public LabelDto _toLabelDto() {
        return new LabelDto(super.getId(), this.subject);
    }

    @Override
    public String toString() {
        return "Label{" +
                "subject='" + subject + '\'' +
                '}';
    }
}
