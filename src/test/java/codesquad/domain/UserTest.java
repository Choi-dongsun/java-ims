package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class UserTest {
    public static final User JAVAJIGI = new User(1L, "javajigi", "123456", "name");
    public static final User SANJIGI = new User(2L, "sanjigi", "123456", "name");

    public static User newUser(Long id) {
        return new User(id, "userId", "pass", "name");
    }

    public static User newUser(String userId) {
        return newUser(userId, "123456");
    }

    public static User newUser(String userId, String password) {
        return new User(1L, userId, password, "name");
    }

    @Test
    public void update_owner() throws Exception {
        User origin = newUser("sanjigi");
        User loginUser = origin;
        User target = new User("sanjigi", "123456", "name2");
        origin.update(loginUser, target);
        assertThat(origin.getName(), is(target.getName()));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_not_owner() throws Exception {
        User origin = newUser("sanjigi");
        User loginUser = newUser("javajigi");
        User target = new User("sanjigi", "123456", "name2");
        origin.update(loginUser, target);
    }

    @Test
    public void update_match_password() {
        User origin = newUser("sanjigi");
        User target = new User("sanjigi", "123456", "name2");
        origin.update(origin, target);
        assertThat(origin.getName(), is(target.getName()));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_mismatch_password() {
        User origin = newUser("sanjigi", "123456");
        User target = new User("sanjigi", "1234567", "name2");
        origin.update(origin, target);
        assertThat(origin.getName(), is(not(target.getName())));
    }

    @Test
    public void match_password() throws Exception {
        User user = newUser("sanjigi");
        assertTrue(user.matchPassword(user.getPassword()));
    }

    @Test
    public void mismatch_password() throws Exception {
        User user = newUser("sanjigi");
        assertFalse(user.matchPassword(user.getPassword() + "2"));
    }
}
