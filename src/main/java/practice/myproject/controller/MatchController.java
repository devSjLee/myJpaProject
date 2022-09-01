package practice.myproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import practice.myproject.domain.Match;
import practice.myproject.domain.MatchDto;
import practice.myproject.repository.MatchRepository;
import practice.myproject.service.MatchService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;
    private final MatchRepository matchRepository;


    @GetMapping("/match/list")
    public ModelAndView matchList(ModelAndView mv) {
        List<Match> matchList = matchRepository.findAll();
        mv.setViewName("match/list");
        mv.addObject("match", matchList);
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
        System.out.println("matchDto = " + matchDto.getMatchTime());
        System.out.println("matchDto = " + matchDto.getTitle());
        System.out.println("matchDto = " + matchDto.getNotice());
        System.out.println("matchDto = " + matchDto.getMatchAddress());
        System.out.println("matchDto = " + matchDto.getLimitedPeople());
        matchService.save(matchDto);


        mv.setViewName("redirect:/match/list");
        return mv;
    }

    @GetMapping("/match/detail")
    public ModelAndView matchDetail(ModelAndView mv, Long id) {
        Optional<Match> findMatch = matchRepository.findById(id);
        if(findMatch.isPresent()) {
            mv.addObject("matchOne", findMatch.get());
        }
        mv.setViewName("match/detail");

        return mv;
    }

    @PostMapping("/match/attend")
    public ModelAndView matchAttend(ModelAndView mv, String loginId, Long matchId) {
        Optional<Match> findMatch = matchRepository.findById(matchId);
        if(findMatch.isPresent()) {
            mv.addObject("matchOne", findMatch.get());
        }
        //매칭신청, 신청한사람이 Member 엔티디에 members,
        matchService.matchAttend(loginId, matchId);

        mv.setViewName("match/detail");
        return mv;
    }


}
