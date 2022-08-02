package practice.myproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import practice.myproject.domain.MemberDto;
import practice.myproject.repository.MemberRepository;
import practice.myproject.service.MemberService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String createForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "member/createMemberForm";
    }

    @PostMapping("/members/create")
    public String create(@Valid MemberDto form, BindingResult result) {
        if(result.hasErrors()) {
            return "member/createMemberForm";
        }

        memberService.save(form);


        return "redirect:/";
    }

}
