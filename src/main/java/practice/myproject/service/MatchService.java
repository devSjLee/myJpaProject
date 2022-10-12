package practice.myproject.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.myproject.domain.*;
import practice.myproject.repository.GroundRepository;
import practice.myproject.repository.MatchRepository;
import practice.myproject.repository.MemberRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final MemberRepository memberRepository;
    private final GroundRepository groundRepository;
    private final EntityManager em;


    @Transactional
    public Match save(MatchDto matchDto, String loginId) {

//        LocalDateTime parseTime = LocalDateTime.parse(matchDto.getMatchTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Ground ground = Ground.builder()
                .x(matchDto.getX())
                .y(matchDto.getY())
                .address(matchDto.getAddress())
                .groundName(matchDto.getGroundName())
                .callNumber(matchDto.getCallNumber())
                .showerYn(matchDto.getShowerYn())
                .shoesYn(matchDto.getShoesYn())
                .sportsWearYn(matchDto.getSportsWearYn())
                .parkingYn(matchDto.getParkingYn())
                .build();

        Ground saveGround = groundRepository.save(ground);

        Match match = Match.builder()
                .ground(saveGround)
                .limitedPeople(matchDto.getLimitedPeople())
                .matchTime(matchDto.getMatchTime())
                .notice(matchDto.getNotice())
                .createBy(loginId)
                .createTime(LocalDateTime.now())
                .build();
        Match match1 = matchRepository.save(match);


        return match1;
    }

    @Transactional
    public void matchAttend(String loginId, Long id) {


        QMember member1 = QMember.member;
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        queryFactory
                .update(member1)
                .set(member1.match.id, id)
                .where(member1.loginId.eq(loginId))
                .execute();
        em.flush();
        em.clear();
    }

    @Transactional
    public Long deleteMatch(Long id) {
        Match findMatch = matchRepository.findById(id).get();
        Long deleteId = findMatch.getId();

        QMember member1 = QMember.member;

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        long execute = queryFactory
                .update(member1)
                .setNull(member1.match.id)
                .where(member1.match.id.eq(deleteId))
                .execute();

        em.flush();
        em.clear();

        matchRepository.deleteById(id);
        return execute;
    }

    @Transactional
    public Long updateMatch(Long id, MatchDto matchDto) {

        QMatch match1 = QMatch.match;
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, match1);
        long execute = jpaUpdateClause
                .set(match1.matchTime, matchDto.getMatchTime())
                .set(match1.notice, matchDto.getNotice())
                .set(match1.limitedPeople, matchDto.getLimitedPeople())
                .where(match1.id.eq(id))
                .execute();
        return execute;

    }



}
