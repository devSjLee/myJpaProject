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

import static practice.myproject.domain.QMatch.*;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final MemberRepository memberRepository;
    private final EntityManager em;


    public Match save(MatchDto matchDto, String loginId) {

        LocalDateTime parseTime = LocalDateTime.parse(matchDto.getMatchTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Match match = Match.builder()
                .title(matchDto.getTitle())
                .matchAddress(matchDto.getMatchAddress())
                .limitedPeople(matchDto.getLimitedPeople())
                .matchTime(parseTime)
                .notice(matchDto.getNotice())
                .createBy(loginId)
                .createTime(LocalDateTime.now())
                .build();
        Match match1 = matchRepository.save(match);
        return match1;
    }

    @Transactional
    public void matchAttend(String loginId, Long id) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMember member1 = QMember.member;
        queryFactory
                .update(member1)
                .set(member1.match.id, id)
                .where(member1.loginId.eq(loginId))
                .execute();
        em.flush();
        em.clear();
    }
}
