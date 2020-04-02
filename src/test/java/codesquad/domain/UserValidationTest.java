package codesquad.domain;

import org.assertj.core.api.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class UserValidationTest {
    private static final Logger log = LoggerFactory.getLogger(UserValidationTest.class);

    private static Validator validator;

    @BeforeClass
    public static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void userId_tooShort() {
        User user = new User("sa", "password" , "name");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }

    @Test
    public void userId_tooLong() {
        User user = new User("123456789012345678901", "password" , "name");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }
    @Test
    public void userId_blank() {
        User user = new User(" ", "password" , "name");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        Assertions.assertThat(constraintViolations.size()).isEqualTo(2);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }

    @Test
    public void password_too_short() {
        User user = new User("javajigi", "pass" , "name");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }

    @Test
    public void password_too_long() {
        User user = new User("javajigi", "123456789012345678901" , "name");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        Assertions.assertThat(constraintViolations.size()).isEqualTo(1);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }

    @Test
    public void password_blank() {
        User user = new User("javajigi", " " , "name");

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        Assertions.assertThat(constraintViolations.size()).isEqualTo(2);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }
}
