package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest {
    private static final Logger log = LoggerFactory.getLogger(UserTest.class);

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

    private static Validator validator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void update_owner() throws Exception {
        User origin = newUser("sanjigi");
        User loginUser = origin;
        User target = new User("sanjigi", "123456", "name2");
        origin.update(loginUser, target);
        assertThat(origin.getName()).isEqualTo(target.getName());
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
        assertThat(origin.getName()).isEqualTo(target.getName());
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_mismatch_password() {
        User origin = newUser("sanjigi", "123456");
        User target = new User("sanjigi", "1234567", "name2");
        origin.update(origin, target);
        assertThat(origin.getName()).isEqualTo(not(target.getName()));
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

    @Test
    public void userId_tooShort() {
        User user = new User("sa", "password" , "name");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertThat(constraintViolations.size()).isEqualTo(1);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }

    @Test
    public void userId_tooLong() {
        User user = new User("123456789012345678901", "password" , "name");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertThat(constraintViolations.size()).isEqualTo(1);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }
    @Test
    public void userId_blank() {
        User user = new User(" ", "password" , "name");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertThat(constraintViolations.size()).isEqualTo(2);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }

    @Test
    public void password_too_short() {
        User user = new User("javajigi", "pass" , "name");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertThat(constraintViolations.size()).isEqualTo(1);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }

    @Test
    public void password_too_long() {
        User user = new User("javajigi", "123456789012345678901" , "name");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertThat(constraintViolations.size()).isEqualTo(1);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }

    @Test
    public void password_blank() {
        User user = new User("javajigi", " " , "name");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        assertThat(constraintViolations.size()).isEqualTo(2);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }
}
