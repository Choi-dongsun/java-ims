package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.*;
import codesquad.dto.IssueDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class IssueService {
    private final IssueRepository issueRepository;
    private final MilestoneService milestoneService;
    private final UserService userService;
    private final LabelService labelService;

    public IssueService(IssueRepository issueRepository, MilestoneService milestoneService,
                        UserService userService, LabelService labelService) {
        this.issueRepository = issueRepository;
        this.milestoneService = milestoneService;
        this.userService = userService;
        this.labelService = labelService;
    }

    @Transactional
    public void add(User loginUser, IssueDto issueDto) {
        issueDto.setWriter(loginUser._toUserDto());
        issueRepository.save(issueDto._toIssue());
    }

    public List<Issue> findAll() {
        return issueRepository.findAllByDeletedFalse();
    }

    public Issue findById(Long id) {
        return issueRepository.findById(id)
                .filter(issue -> !issue.isDeleted())
                .orElseThrow(EntityNotFoundException::new);
    }

    public Issue findById(User loginUser, Long id) throws RuntimeException {
        return issueRepository.findById(id)
                .filter(issue -> !issue.isDeleted())
                .filter(issue -> issue.isWriter(loginUser))
                .orElseThrow(UnAuthorizedException::new);
    }

    @Transactional
    public void update(User loginUser, Long id, IssueDto target) throws RuntimeException {
        Issue original = findById(loginUser, id);
        original.update(loginUser, target);
    }

    @Transactional
    public void delete(User loginUser, Long id) {
        Issue issue = findById(loginUser, id);
        issue.delete();
    }

    @Transactional
    public void decideMilestone(User loginUser, Long issueId, Long milestoneId) {
        Milestone milestone = milestoneService.findById(milestoneId);
        findById(loginUser, issueId).decideMilestone(milestone);
    }

    @Transactional
    public void decideAssignee(User loginUser, Long issueId, Long userId) {
        User user = userService.findById(userId);
        findById(loginUser, issueId).decideAssignee(user);
    }

    @Transactional
    public void decideLabel(User loginUser, Long issueId, Long labelId) {
        Label label = labelService.findById(labelId);
        findById(loginUser, issueId).decideLabel(label);

    }
}
