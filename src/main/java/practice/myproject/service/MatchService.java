package practice.myproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.myproject.domain.Match;
import practice.myproject.domain.MatchDto;
import practice.myproject.repository.MatchRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    public void save(MatchDto matchDto) {

        LocalDateTime parseTime = LocalDateTime.parse(matchDto.getMatchTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Match match = Match.builder()
                .title(matchDto.getTitle())
                .matchAddress(matchDto.getMatchAddress())
                .limitedPeople(matchDto.getLimitedPeople())
                .matchTime(parseTime)
                .notice(matchDto.getNotice())
                .createTime(LocalDateTime.now())
                .build();
        matchRepository.save(match);
    }
}
