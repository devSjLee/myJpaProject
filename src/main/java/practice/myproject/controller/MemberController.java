package practice.myproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import practice.myproject.domain.MemberDto;
import practice.myproject.domain.MemberLoginDto;
import practice.myproject.repository.MemberRepository;
import practice.myproject.service.MemberService;

import javax.servlet.http.HttpSession;
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
    public ModelAndView loginForm(ModelAndView mv,
                                  @RequestParam(value = "error", required = false)String error,
                                  @RequestParam(value = "exception", required = false)String exception) {
        mv.addObject("memberLoginDto", new MemberLoginDto());
        mv.setViewName("member/login");
        mv.addObject("error", error);
        mv.addObject("exception", exception);

        return mv;
    }

    @PostMapping("/loginProc")
    public ModelAndView login(ModelAndView mv, MemberLoginDto memberLoginDto) {
        System.out.println("타냐");
        mv.setViewName("index");

        return mv;
    }


    @GetMapping("/members/create")
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
        mv.setViewName("redirect:/login");


        return mv;
    }

    @PostMapping("/members/checkDuplicateId")
    public Boolean checkDuplicatedId(@RequestParam("loginId") String loginId) {
        System.out.println("test: "+loginId);
        Boolean result = memberService.checkDuplicatedId(loginId);
        System.out.println("result = " + result);
        return result;
    }





}
