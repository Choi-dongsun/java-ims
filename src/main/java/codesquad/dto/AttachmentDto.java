package codesquad.dto;

public class AttachmentDto {

    private Long id;
    private String originName;
    private String manageName;

    public AttachmentDto(Long id, String originName, String manageName) {
        this.id = id;
        this.originName = originName;
        this.manageName = manageName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getManageName() {
        return manageName;
    }

    public void setManageName(String manageName) {
        this.manageName = manageName;
    }
}
