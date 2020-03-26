package codesquad.web;

import codesquad.domain.User;
import codesquad.dto.LabelDto;
import codesquad.security.LoginUser;
import codesquad.service.LabelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/labels")
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping("/form")
    public String createForm(@LoginUser User loginUser) {
        return "/label/form";
    }

    @PostMapping
    public String create(@LoginUser User loginUser, LabelDto labelDto) {
        labelService.add(labelDto);
        return "redirect:/labels";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("labels", labelService.findAll());
        return "/label/list";
    }
}
