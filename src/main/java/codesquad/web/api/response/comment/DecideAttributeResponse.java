package codesquad.web.api.response.comment;

public class DecideAttributeResponse {
    private String userId;
    private String name;
    private String createDate;

    public DecideAttributeResponse() {
    }

    public DecideAttributeResponse(String userId, String name, String createDate) {
        this.userId = userId;
        this.name = name;
        this.createDate = createDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
