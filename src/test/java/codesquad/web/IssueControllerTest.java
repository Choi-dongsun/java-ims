package codesquad.web;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class IssueControllerTest extends BasicAuthAcceptanceTest {

    @Test
    public void upload() throws Exception {
        //given
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("file", new ClassPathResource("logback.xml"))
                .build();

        //when
        ResponseEntity<String> result = basicAuthTemplate.postForEntity("/issues/1/attachments", request, String.class);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    public void upload_no_login() throws Exception {
        //given
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("file", new ClassPathResource("logback.xml"))
                .build();

        //when
        ResponseEntity<String> result = template.postForEntity("/issues/1/attachments", request, String.class);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}