package practice.myproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import practice.myproject.domain.Member;
import practice.myproject.domain.MemberDto;
import practice.myproject.domain.MemberLoginDto;
import practice.myproject.repository.MemberRepository;
import practice.myproject.service.MemberService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

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
    public ModelAndView loginForm(ModelAndView mv) {
        mv.addObject("memberLoginDto", new MemberLoginDto());
        System.out.println("여기?");
        mv.setViewName("member/login");
        return mv;
    }

    @PostMapping("/login")
    public ModelAndView login(ModelAndView mv, Model model, MemberLoginDto memberLoginDto, HttpSession session) {
        Optional<Member> member = memberRepository.findByLoginId(memberLoginDto.getLoginId());
        if(member.isPresent()) {
            Member findMember = member.get();
            if(findMember.getPassword().equals(memberLoginDto.getPassword())) {
                System.out.println("memberLoginDto = " + memberLoginDto.getLoginId());
                System.out.println("memberLoginDto = " + memberLoginDto.getPassword());
                session.setAttribute("member", findMember);
                session.setMaxInactiveInterval(30*60);  //세션시간 30분 (초단위)
                mv.setViewName("index");
            }
        }else {
            mv.setViewName("redirect:login");
        }

        return mv;
    }

    @GetMapping("/logout")
    public ModelAndView logout(ModelAndView mv, HttpSession session) {
//        session.invalidate();   //모든세션 만료
        session.removeAttribute("member");
        mv.setViewName("redirect:/");
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
        mv.setViewName("redirect:/");


        return mv;
    }

    @GetMapping("/member/checkDuplicateId")
    public Boolean checkDuplicatedId(@RequestParam("loginId") String loginId) {
        return memberService.checkDuplicatedId(loginId);
    }





}
