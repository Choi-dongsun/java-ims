package codesquad.web.api.response.comment;

public class CommentResponse {
    private String commentId;
    private String userId;
    private String content;
    private String createdDate;

    public CommentResponse(Long commentId, String userId, String content, String createdDate) {
        this.commentId = String.valueOf(commentId);
        this.userId = userId;
        this.content = content;
        this.createdDate = createdDate;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = String.valueOf(commentId);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}
