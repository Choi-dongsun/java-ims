package codesquad.web;

import codesquad.domain.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

    @Test
    public void createForm() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate.getForEntity("/issues/form", String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void createForm_no_login() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void create() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "제목입니다")
                .addParameter("comment", "내용입니다").build();

        ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issues", request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void create_no_login() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "제목입니다")
                .addParameter("comment", "내용입니다").build();

        ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void findAll() {
        ResponseEntity<String> response = template.getForEntity("/issues", String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void detail() {
        Long issueId = 1L;

        ResponseEntity<String> response = template.getForEntity("/issues/" + issueId, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void updateForm() {
        Long loginUserId = loginUser.getId();

        ResponseEntity<String> response = basicAuthTemplate.getForEntity("/issues/" + loginUserId + "/form", String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void updateForm_no_login() {
        Long loginUserId = loginUser.getId();

        ResponseEntity<String> response = template.getForEntity("/issues/" + loginUserId + "/form", String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void updateForm_otherUser_access() {
        Long loginUserId = loginUser.getId();
        User otherUser = new User("sanjigi", "123456", "산지기");

        ResponseEntity<String> response = basicAuthTemplate(otherUser)
                .getForEntity("/issues/" + loginUserId + "/form", String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
