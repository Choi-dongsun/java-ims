package codesquad.web.api.response.comment;

public class UpdateFormCommentResponse {

    private String Content;

    public UpdateFormCommentResponse(String content) {
        Content = content;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
