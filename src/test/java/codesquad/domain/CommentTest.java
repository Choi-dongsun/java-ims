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

import static codesquad.domain.UserTest.JAVAJIGI;
import static org.assertj.core.api.Assertions.assertThat;

public class CommentTest {
    private static final Logger log = LoggerFactory.getLogger(UserTest.class);

    private static Validator validator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void subject_empty() {
        Issue issue = new Issue("subject", "comment", JAVAJIGI);
        Comment comment = new Comment(JAVAJIGI, "", issue);

        Set<ConstraintViolation<Comment>> constraintViolations = validator.validate(comment);

        assertThat(constraintViolations.size()).isEqualTo(1);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }
}