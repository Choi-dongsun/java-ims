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

import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.SANJIGI;
import static org.assertj.core.api.Assertions.assertThat;

public class IssueTest {
    private static final Logger log = LoggerFactory.getLogger(UserTest.class);

    private static Validator validator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void match_writer() {
        Issue origin = new Issue("subject", "comment", JAVAJIGI);

        boolean result = origin.isWriter(JAVAJIGI);

        assertThat(result).isTrue();
    }

    @Test
    public void mismatch_writer() {
        Issue origin = new Issue("subject", "comment", JAVAJIGI);

        boolean result = origin.isWriter(SANJIGI);

        assertThat(result).isFalse();
    }

    @Test
    public void update() {
        Issue origin = new Issue("subject", "comment", JAVAJIGI);
        Issue target = new Issue("subject1", "comment1", JAVAJIGI);

        origin.update(JAVAJIGI, target._toIssueDto());

        assertThat(origin.getSubject()).isEqualTo(target.getSubject());
        assertThat(origin.getComment()).isEqualTo(target.getComment());
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_mismatch_writer() {
        Issue origin = new Issue("subject", "comment", JAVAJIGI);
        Issue target = new Issue("subject1", "comment1", JAVAJIGI);

        origin.update(SANJIGI, target._toIssueDto());

        assertThat(origin.getSubject()).isEqualTo(target.getSubject());
        assertThat(origin.getComment()).isEqualTo(target.getComment());
    }

    @Test
    public void subject_too_short() {
        Issue issue = new Issue("su", "comment", JAVAJIGI);

        Set<ConstraintViolation<Issue>> constraintViolations = validator.validate(issue);

        assertThat(constraintViolations.size()).isEqualTo(1);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }

    @Test
    public void subject_too_long() {
        StringBuilder result = new StringBuilder();
        String str = "1234567890";
        for (int i = 0; i < 11; i++) {
            result.append(str);
        }

        log.debug("Subject size : {}", result.toString().length());
        Issue issue = new Issue(result.toString(), "comment", JAVAJIGI);

        Set<ConstraintViolation<Issue>> constraintViolations = validator.validate(issue);

        assertThat(constraintViolations.size()).isEqualTo(1);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }

    @Test
    public void subject_blank() {
        Issue issue = new Issue(" ", "comment", JAVAJIGI);

        Set<ConstraintViolation<Issue>> constraintViolations = validator.validate(issue);

        assertThat(constraintViolations.size()).isEqualTo(2);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }

    @Test
    public void comment_too_short() {
        Issue issue = new Issue("subject", "co", JAVAJIGI);

        Set<ConstraintViolation<Issue>> constraintViolations = validator.validate(issue);

        assertThat(constraintViolations.size()).isEqualTo(1);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }

    @Test
    public void comment_empty() {
        Issue issue = new Issue("subject", "", JAVAJIGI);

        Set<ConstraintViolation<Issue>> constraintViolations = validator.validate(issue);

        assertThat(constraintViolations.size()).isEqualTo(2);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }
}
