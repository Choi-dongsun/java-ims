package codesquad.web;

import codesquad.domain.User;
import codesquad.dto.UserDto;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class ApiUserAcceptanceTest extends AcceptanceTest {

    @Test
    public void create() throws Exception {
        UserDto newUser = createUserDto("testuser1");
        String location = createResource("/api/users", newUser);

        UserDto dbUser = getResource(location, UserDto.class, findByUserId(newUser.getUserId()));
        softly.assertThat(dbUser.getUserId()).isEqualTo(newUser.getUserId());
        softly.assertThat(dbUser.getName()).isEqualTo(newUser.getName());
        softly.assertThat(dbUser.getPassword()).isEqualTo(newUser.getPassword());
    }

    @Test
    public void show_다른_사람() throws Exception {
        UserDto newUser = createUserDto("testuser2");
        String location = createResource("/api/users", newUser);

        ResponseEntity<String> response = getResource(location, findDefaultUser());
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    private UserDto createUserDto(String userId) {
        return new UserDto(userId, "password", "name");
    }

    @Test
    public void update() throws Exception {
        UserDto newUser = createUserDto("testuser3");
        String location = createResource("/api/users", newUser);

        User loginUser = findByUserId(newUser.getUserId());
        UserDto updateUser = new UserDto(newUser.getUserId(), "password", "name2");
        basicAuthTemplate(loginUser).put(location, updateUser);

        UserDto dbUser = getResource(location, UserDto.class, findByUserId(newUser.getUserId()));
        softly.assertThat(dbUser.getUserId()).isEqualTo(updateUser.getUserId());
        softly.assertThat(dbUser.getPassword()).isEqualTo(updateUser.getPassword());
        softly.assertThat(dbUser.getName()).isEqualTo(updateUser.getName());
    }

    @Test
    public void update_다른_사람() throws Exception {
        UserDto newUser = createUserDto("testuser4");
        String location = createResource("/api/users", newUser);

        UserDto updateUser = new UserDto(newUser.getUserId(), "password", "name2");
        basicAuthTemplate(findDefaultUser()).put(location, updateUser);

        UserDto dbUser = getResource(location, UserDto.class, findByUserId(newUser.getUserId()));
        softly.assertThat(dbUser.getUserId()).isEqualTo(updateUser.getUserId());
        softly.assertThat(dbUser.getPassword()).isEqualTo(updateUser.getPassword());
        softly.assertThat(dbUser.getName()).isNotEqualTo(updateUser.getName());
    }

    @Test
    public void login() {
        UserDto userDto = new UserDto("javajigi", "123456", "name");

        ResponseEntity<Void> response = template.postForEntity("/api/users/login", userDto, Void.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }
}
