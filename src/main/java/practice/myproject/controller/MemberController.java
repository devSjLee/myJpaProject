package practice.myproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import practice.myproject.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

}
