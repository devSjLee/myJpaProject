package practice.myproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import practice.myproject.domain.Ground;
import practice.myproject.domain.Match;
import practice.myproject.domain.MatchDto;
import practice.myproject.domain.Member;
import practice.myproject.repository.GroundRepository;
import practice.myproject.repository.MatchRepository;
import practice.myproject.repository.MemberRepository;
import practice.myproject.service.MatchService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MatchController {

    private final MatchService matchService;
    private final MatchRepository matchRepository;
    private final MemberRepository memberRepository;
    private final GroundRepository groundRepository;


    @GetMapping("/match/list")
    public ModelAndView matchList(ModelAndView mv, String message, String dateKey, String keyWord,
                                  @PageableDefault Pageable pageable) {
        log.info("리스트 페이지 호출");
        LocalDateTime now = LocalDateTime.now();
        ArrayList<Object> dateArr = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dateArr.add(now.plusDays(i));
        }

        Page<Match> matchList = matchService.findMatchList(dateKey, keyWord, pageable);
        log.info("리스트 페이징, 동적쿼리 Page 객체로 정상 반환");
        mv.addObject("matchList", matchList);
        if(matchList.getTotalPages() == 0) {
            mv.addObject("matchList", null);
        }
        if (dateKey != null) {
            mv.addObject("activeKey", dateKey);
        }

        mv.addObject("message", message);
        mv.addObject("keyWord", keyWord);
        mv.addObject("dateArr", dateArr);
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
    public ModelAndView createMatch(ModelAndView mv, @Valid MatchDto matchDto, BindingResult result, String loginId) {
        System.out.println("테스트");
        if(result.hasErrors()) {
        System.out.println("테스트1");
            mv.setViewName("match/createMatchForm");
            return mv;
        }
        Match match = matchService.save(matchDto, loginId);
        log.info("매칭 생성 완료!!");

        //매칭만든사람 자동으로 해당 매칭에 가입
        matchService.matchAttend(loginId, match.getId());
        log.info("매칭생성자 해당매칭에 자동가입완료!!");

        mv.setViewName("redirect:/match/list");
        return mv;
    }

    @GetMapping("/match/detail")
    public ModelAndView matchDetail(ModelAndView mv, Long id, String loginId, String message) {

        if (id != null) {
            Optional<Match> findMatch = matchRepository.findById(id);
            if (findMatch.isPresent()) {
                mv.addObject("matchOne", findMatch.get());
                mv.addObject("createdBy", findMatch.get().getCreateBy());
                for (Member member : findMatch.get().getMembers()) {
                    if (member.getLoginId().equals(loginId)) {
                        mv.addObject("attendedId", loginId);
                    }
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
        log.info("매칭 참여!!");
        //리다이렉트시 필요한 공통 변수
        redirectAttributes.addAttribute("id", id);
        redirectAttributes.addAttribute("loginId", loginId);

        Optional<Match> match = matchRepository.findById(id);
        if (match.isPresent()) {
            Match findMatch = match.get();
            for (Member member : findMatch.getMembers()) {
                if (member.getLoginId().equals(loginId)) {
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

    @GetMapping("/match/{id}")
    public ModelAndView updateMatchForm(ModelAndView mv, @PathVariable("id") Long id) {

        Optional<Match> match = matchRepository.findById(id);
        System.out.println("아이디 오냐"+match.get().getGround().getCallNumber());
        System.out.println("아이디 오냐"+match.get().getGround().getGroundName());

        List<Ground> findGround = groundRepository.findAll();

        mv.addObject("groundList", findGround);
        mv.addObject("matchOne", match.get());
        mv.addObject("matchDto", new MatchDto());
        mv.setViewName("match/updateMatchForm");
        return mv;
    }

    @PutMapping("/match/{id}")
    public ModelAndView updateMatch(ModelAndView mv, @PathVariable("id") Long id, MatchDto matchDto, String loginId, RedirectAttributes redirectAttributes
    , Long groundId) {

        Long result = matchService.updateMatch(id, matchDto, groundId);
        if (result != null) {
            redirectAttributes.addAttribute("id", id);
            redirectAttributes.addAttribute("loginId", loginId);
            redirectAttributes.addAttribute("message", "수정완료!!");
        }
        mv.setViewName("redirect:/match/detail");
        return mv;
    }

    @DeleteMapping("/match/{id}")
    public ModelAndView deleteMatch(ModelAndView mv, @PathVariable Long id, RedirectAttributes redirectAttributes) {

        Long aLong = matchService.deleteMatch(id);
        System.out.println("뭐지: " + aLong);
        redirectAttributes.addAttribute("message", "삭제 완료!!");
        mv.setViewName("redirect:/match/list");
        return mv;
    }


}
