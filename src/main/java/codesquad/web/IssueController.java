package codesquad.web;

import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/form")
    public String createForm(@LoginUser User loginUser) {
        return "/issue/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, IssueDto issueDto) {
        issueService.add(loginUser, issueDto);
        return "redirect:/";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("issues", issueService.findAll());
        return  "/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("issue", issueService.findById(id)._toIssueDto());
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
        return "redirect:/issues/" + id;
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable Long id) {
        issueService.delete(loginUser, id);
        return "redirect:/";
    }
}
