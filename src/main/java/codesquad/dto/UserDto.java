package codesquad.dto;

import codesquad.domain.User;

import javax.validation.constraints.Size;
import java.util.Objects;

public class UserDto {

    private Long id = 0L;

    @Size(min = 3, max = 20)
    private String userId;

    @Size(min = 6, max = 20)
    private String password;

    @Size(min = 3, max = 20)
    private String name;

    public UserDto() {
    }

    public UserDto(String userId, String password, String name) {
        this(0L, userId, password, name);
    }

    public UserDto(Long id, String userId, String password, String name) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User _toUser() {
        return new User(id, this.userId, this.password, this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(getId(), userDto.getId()) &&
                Objects.equals(getUserId(), userDto.getUserId()) &&
                Objects.equals(getPassword(), userDto.getPassword()) &&
                Objects.equals(getName(), userDto.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getPassword(), getName());
    }

    @Override
    public String toString() {
        return "UserDto [id=" + id + ", userId=" + userId + ", password=" + password + ", name=" + name + "]";
    }
}
