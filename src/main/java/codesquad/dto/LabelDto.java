package codesquad.dto;

import codesquad.domain.Label;

public class LabelDto {

    private Long id;
    private String subject;

    public LabelDto() {
    }

    public LabelDto(Long id, String subject) {
        this.id = id;
        this.subject = subject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
