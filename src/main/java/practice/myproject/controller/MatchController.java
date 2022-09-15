package practice.myproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import practice.myproject.domain.Match;
import practice.myproject.domain.MatchDto;
import practice.myproject.domain.Member;
import practice.myproject.repository.MatchRepository;
import practice.myproject.repository.MemberRepository;
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
    private final MemberRepository memberRepository;


    @GetMapping("/match/list")
    public ModelAndView matchList(ModelAndView mv, String message) {
        List<Match> matchList = matchRepository.findAll(Sort.by(Sort.Direction.ASC, "matchTime"));
        mv.addObject("match", matchList);
        mv.addObject("message", message);
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
    public ModelAndView createMatch(ModelAndView mv, MatchDto matchDto, String loginId) {
        Match match = matchService.save(matchDto, loginId);

        //매칭만든사람 자동으로 해당 매칭에 가입
        System.out.println("이게뭐지: "+loginId);
        matchService.matchAttend(loginId, match.getId());

        mv.setViewName("redirect:/match/list");
        return mv;
    }

    @GetMapping("/match/detail")
    public ModelAndView matchDetail(ModelAndView mv, Long id, String loginId, String message) {
        //회원이 참여중인 매칭이 있는지 확인
        Optional<Member> member1 = memberRepository.findByLoginId(loginId);
        if(member1.isPresent()) {
            if(member1.get().getMatch() != null) {
                mv.addObject("checkMatchMsg", "기존에 참여중인 매칭이 있습니다. 새로운 매칭에 참여하시겠습니까?? (기존에 참여중인 매칭은 삭제)");
            }
        }

        Optional<Match> findMatch = matchRepository.findById(id);
        if(findMatch.isPresent()) {
            mv.addObject("matchOne", findMatch.get());
            mv.addObject("createdBy", findMatch.get().getCreateBy());
            for (Member member : findMatch.get().getMembers()) {
                if(member.getLoginId().equals(loginId)) {
                    mv.addObject("attendedId", loginId);
                }
            }
        }
        mv.addObject("id", id);
        mv.addObject("loginId", loginId);
        mv.addObject("message", message);
        mv.setViewName("match/detail");

        return mv;
    }

    @PostMapping("/match/attend")
    public ModelAndView matchAttend(ModelAndView mv, String loginId, Long id, RedirectAttributes redirectAttributes) {
        //리다이렉트시 필요한 공통 변수
        redirectAttributes.addAttribute("id", id);
        redirectAttributes.addAttribute("loginId", loginId);

        Optional<Match> match = matchRepository.findById(id);
        if(match.isPresent()) {
            Match findMatch = match.get();
            for (Member member : findMatch.getMembers()) {
                if(member.getLoginId().equals(loginId)) {
                    redirectAttributes.addAttribute("message", "이미 가입중인 매칭입니다.");
                    mv.setViewName("redirect:/match/detail");
                    return mv;

                }
            }
        }

        //매칭신청, 신청한사람이 Member 엔티디에 members,
        matchService.matchAttend(loginId, id);

        redirectAttributes.addAttribute("message", "매칭 참여 완료!!");
        mv.setViewName("redirect:/match/detail");
        return mv;
    }

    @DeleteMapping("/match/{id}")
    public ModelAndView deleteMatch(ModelAndView mv, @PathVariable Long id, RedirectAttributes redirectAttributes) {

        Long aLong = matchService.deleteMatch(id);
        System.out.println("뭐지: "+aLong);
        redirectAttributes.addAttribute("message", "삭제 완료!!");
        mv.setViewName("redirect:/match/list");
        return mv;
    }


}
