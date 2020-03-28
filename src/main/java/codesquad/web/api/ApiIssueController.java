package codesquad.web.api;

import codesquad.domain.User;
import codesquad.dto.LabelDto;
import codesquad.dto.MilestoneDto;
import codesquad.dto.UserDto;
import codesquad.security.LoginUser;
import codesquad.service.*;
import codesquad.web.api.response.ApiResponse;
import codesquad.web.api.response.comment.DecideAttributeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/issues")
public class ApiIssueController {

    private final IssueService issueService;
    private final MilestoneService milestoneService;
    private final UserService userService;
    private final LabelService labelService;
    private final CommentService commentService;

    public ApiIssueController(IssueService issueService, MilestoneService milestoneService,
                           UserService userService, LabelService labelService, CommentService commentService) {
        this.issueService = issueService;
        this.milestoneService = milestoneService;
        this.userService = userService;
        this.labelService = labelService;
        this.commentService = commentService;
    }

    @GetMapping("/{issueId}/milestones/{milestoneId}")
    public ApiResponse decideMilestone(@LoginUser User loginUser, @PathVariable Long issueId, @PathVariable Long milestoneId) {
        MilestoneDto milestone = issueService.decideMilestone(loginUser, issueId, milestoneId);
        commentService.addAttributeComment(loginUser, issueId, milestone.getSubject());

        DecideAttributeResponse data = new DecideAttributeResponse(
                loginUser.getUserId(), milestone.getSubject(), LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return new ApiResponse(true, data);
    }

    @GetMapping("/{issueId}/labels/{labelId}")
    public ApiResponse decideLabel(@LoginUser User loginUser, @PathVariable Long issueId, @PathVariable Long labelId) {
        LabelDto label = issueService.decideLabel(loginUser, issueId, labelId);
        commentService.addAttributeComment(loginUser, issueId, label.getSubject());

        DecideAttributeResponse data = new DecideAttributeResponse(
                loginUser.getUserId(), label.getSubject(), LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return new ApiResponse(true, data);
    }

    @GetMapping("/{issueId}/assignees/{assigneeId}")
    public ApiResponse decideAssignee(@LoginUser User loginUser, @PathVariable Long issueId, @PathVariable Long assigneeId) {
        UserDto assignee = issueService.decideAssignee(loginUser, issueId, assigneeId);
        commentService.addAttributeComment(loginUser, issueId, assignee.getName());

        DecideAttributeResponse data = new DecideAttributeResponse(
                loginUser.getUserId(), assignee.getName(), LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return new ApiResponse(true, data);
    }
}
