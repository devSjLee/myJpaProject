package practice.myproject.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.myproject.domain.*;
import practice.myproject.repository.MatchRepository;
import practice.myproject.repository.MemberRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final MemberRepository memberRepository;
    private final EntityManager em;


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

    @Transactional
    public void matchAttend(String loginId, Long matchId) {
        //회원 가져오기
        Optional<Member> member = memberRepository.findByLoginId(loginId);
        Member findMember = member.get();

        Optional<Match> match1 = matchRepository.findById(matchId);
        Match findMatch = match1.get();
        findMatch.getMembers().add(findMember);

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMatch match = QMatch.match;

        queryFactory
                .update(match)
                .set(match.members, findMatch.getMembers())
                .where(match.id.eq(matchId))
                .execute();

    }
}
