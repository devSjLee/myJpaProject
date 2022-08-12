package practice.myproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.myproject.domain.Match;
import practice.myproject.domain.MatchDto;
import practice.myproject.repository.MatchRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    public void save(MatchDto matchDto) {
        Match match = Match.builder()
                .title(matchDto.getTitle())
                .matchAddress(matchDto.getMatchAddress())
                .limitedPeople(matchDto.getLimitedPeople())
                .matchTime(matchDto.getMatchTime())
                .notice(matchDto.getNotice())
                .createTime(LocalDateTime.now())
                .build();
        matchRepository.save(match);
    }
}
