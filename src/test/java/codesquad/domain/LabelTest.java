package codesquad.domain;

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

public class LabelTest {
    private static final Logger log = LoggerFactory.getLogger(UserTest.class);

    private static Validator validator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void subject_empty() {
        Label label = new Label(" ");

        Set<ConstraintViolation<Label>> constraintViolations = validator.validate(label);

        assertThat(constraintViolations.size()).isEqualTo(1);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }
}