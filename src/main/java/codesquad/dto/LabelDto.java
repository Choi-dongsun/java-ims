package codesquad.dto;

import codesquad.domain.Label;

public class LabelDto {

    private String subject;

    public LabelDto(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Label _toLabel() {
        return new Label(subject);
    }
}
