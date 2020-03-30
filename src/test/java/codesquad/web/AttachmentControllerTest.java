package codesquad.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.BasicAuthAcceptanceTest;

import static org.assertj.core.api.Assertions.assertThat;

public class AttachmentControllerTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(AttachmentControllerTest.class);

    @Test
    public void download() throws Exception {
        //when
        ResponseEntity<String> result = template.getForEntity("/attachments/1", String.class);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", result.getBody());
    }
}