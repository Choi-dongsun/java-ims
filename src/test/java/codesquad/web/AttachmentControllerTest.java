package codesquad.web;

import codesquad.domain.Attachment;
import codesquad.domain.Issue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.BasicAuthAcceptanceTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static codesquad.domain.UserTest.JAVAJIGI;
import static org.assertj.core.api.Assertions.assertThat;

public class AttachmentControllerTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(AttachmentControllerTest.class);

    private static Validator validator;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void download() throws Exception {
        //when
        ResponseEntity<String> result = template.getForEntity("/attachments/1", String.class);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", result.getBody());
    }

    @Test
    public void originName_empty() {
        Issue issue = new Issue("subject", "comment", JAVAJIGI);
        Attachment attachment = new Attachment("", "manageName", JAVAJIGI, issue);

        Set<ConstraintViolation<Attachment>> constraintViolations = validator.validate(attachment);

        assertThat(constraintViolations.size()).isEqualTo(1);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }

    @Test
    public void manageName_empty() {
        Issue issue = new Issue("subject", "comment", JAVAJIGI);
        Attachment attachment = new Attachment("originalName", "", JAVAJIGI, issue);

        Set<ConstraintViolation<Attachment>> constraintViolations = validator.validate(attachment);

        assertThat(constraintViolations.size()).isEqualTo(1);
        constraintViolations.forEach(i -> log.debug(i.getMessage()));
    }
}