package practice.myproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import practice.myproject.domain.MemberDto;
import practice.myproject.repository.MemberRepository;
import practice.myproject.service.MemberService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/")
    public ModelAndView index(ModelAndView mv) {
        mv.setViewName("index");
        return mv;
    }

    @GetMapping("/login")
    public ModelAndView createForm(ModelAndView mv) {
        mv.addObject("memberDto", new MemberDto());
        mv.setViewName("member/createMemberForm");
        return mv;
    }

    @PostMapping("/members/create")
    public ModelAndView create(ModelAndView mv, @Valid MemberDto form, BindingResult result) {
        if(result.hasErrors()) {
            mv.setViewName("member/createMemberForm");
            return mv;
        }

        memberService.save(form);
        System.out.println("111");
        mv.setViewName("redirect:/");
        System.out.println("22");


        return mv;
    }

    @GetMapping("/member/checkDuplicateId")
    public Boolean checkDuplicatedId(@RequestParam("loginId") String loginId) {
        Boolean check = memberService.checkDuplicatedId(loginId);
        System.out.println("test: "+check);
        return check;
    }

}
