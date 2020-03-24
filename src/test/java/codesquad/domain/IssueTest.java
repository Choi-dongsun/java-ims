package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Test;

import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.SANJIGI;
import static org.assertj.core.api.Assertions.assertThat;

public class IssueTest {

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
}
