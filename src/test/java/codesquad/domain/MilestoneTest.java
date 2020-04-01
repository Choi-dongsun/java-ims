package codesquad.domain;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class MilestoneTest {
    private static final Logger log = LoggerFactory.getLogger(UserTest.class);

    private static Validator validator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void subject_blank() {
        Milestone milestone = new Milestone(" ", LocalDateTime.now(), LocalDateTime.parse("2020-12-31T02:12:34"));

        Set<ConstraintViolation<Milestone>> constraintViolations = validator.validate(milestone);

        assertThat(constraintViolations.size()).isEqualTo(1);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }

    @Test
    public void startDate_null() {
        Milestone milestone = new Milestone("subject", null, LocalDateTime.parse("2020-12-31T02:12:34"));

        Set<ConstraintViolation<Milestone>> constraintViolations = validator.validate(milestone);

        assertThat(constraintViolations.size()).isEqualTo(1);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }

    @Test
    public void endDate_null() {
        Milestone milestone = new Milestone("subject", LocalDateTime.now(), null);

        Set<ConstraintViolation<Milestone>> constraintViolations = validator.validate(milestone);

        assertThat(constraintViolations.size()).isEqualTo(1);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }

    @Test
    public void endDate_not_future() {
        Milestone milestone = new Milestone("subject", LocalDateTime.now(), LocalDateTime.parse("2002-12-31T02:12:34"));

        Set<ConstraintViolation<Milestone>> constraintViolations = validator.validate(milestone);

        assertThat(constraintViolations.size()).isEqualTo(1);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }
}