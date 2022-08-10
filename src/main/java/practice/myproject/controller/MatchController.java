package practice.myproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import practice.myproject.domain.MatchDto;
import practice.myproject.repository.MatchRepository;
import practice.myproject.service.MatchService;

@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;
    private final MatchRepository matchRepository;


    @GetMapping("/match/list")
    public ModelAndView matchList(ModelAndView mv) {
        mv.setViewName("match/list");
        return mv;
    }

    @GetMapping("/match/createMatchForm")
    public ModelAndView createMatchForm(ModelAndView mv) {
        mv.addObject("matchDto", new MatchDto());
        mv.setViewName("match/createMatchForm");
        return mv;
    }

    @PostMapping("/match/create")
    public ModelAndView createMatch(ModelAndView mv, MatchDto matchDto) {

        mv.setViewName("redirect:/match/list");
        return mv;
    }

}
