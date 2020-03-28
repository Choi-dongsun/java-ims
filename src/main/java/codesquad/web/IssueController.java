package codesquad.web;

import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;
    private final MilestoneService milestoneService;
    private final UserService userService;
    private final LabelService labelService;
    private final CommentService commentService;

    public IssueController(IssueService issueService, MilestoneService milestoneService,
                           UserService userService, LabelService labelService, CommentService commentService) {
        this.issueService = issueService;
        this.milestoneService = milestoneService;
        this.userService = userService;
        this.labelService = labelService;
        this.commentService = commentService;
    }

    @GetMapping("/form")
    public String createForm(@LoginUser User loginUser) {
        return "/issue/form";
    }

    @PostMapping
    public String create(@LoginUser User loginUser, IssueDto issueDto) {
        issueService.add(loginUser, issueDto);
        return "redirect:/";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("issues", issueService.findAll());
        return  "/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("issue", issueService.findById(id)._toIssueDto());
        model.addAttribute("milestones", milestoneService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("labels", labelService.findAll());
        model.addAttribute("comments", commentService.findAllByIssue(id));
        return "/issue/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable Long id, Model model) {
        model.addAttribute("issue", issueService.findById(loginUser, id)._toIssueDto());
        return "/issue/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable Long id, IssueDto target) {
        issueService.update(loginUser, id, target);
        return "redirect:/issues/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable Long id) {
        issueService.delete(loginUser, id);
        return "redirect:/";
    }
}
