package practice.myproject.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.myproject.domain.*;
import practice.myproject.repository.GroundRepository;
import practice.myproject.repository.MatchRepository;
import practice.myproject.repository.MemberRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

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
                .placeUrl(matchDto.getPlaceUrl())
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
    public Long updateMatch(Long id, MatchDto matchDto, Long groundId) {

        QGround ground = QGround.ground;
        JPAUpdateClause jpaUpdateClause2 = new JPAUpdateClause(em, ground);
        jpaUpdateClause2
                .set(ground.groundName, matchDto.getGroundName())
                .set(ground.address, matchDto.getAddress())
                .set(ground.callNumber, matchDto.getCallNumber())
                .set(ground.parkingYn, matchDto.getParkingYn())
                .set(ground.shoesYn, matchDto.getShoesYn())
                .set(ground.showerYn, matchDto.getShowerYn())
                .set(ground.sportsWearYn, matchDto.getSportsWearYn())
                .set(ground.placeUrl, matchDto.getPlaceUrl())
                .set(ground.x, matchDto.getX())
                .set(ground.y, matchDto.getY())
                .where(ground.id.eq(groundId))
                .execute();

        QMatch match1 = QMatch.match;
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, match1);
        long execute = jpaUpdateClause
                .set(match1.matchTime, matchDto.getMatchTime())
                .set(match1.notice, matchDto.getNotice())
                .set(match1.limitedPeople, matchDto.getLimitedPeople())
                .where(match1.id.eq(id))
                .execute();

        em.flush();
        em.clear();
        return execute;

    }


    public Page<Match> findMatchList2(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber()-1);
        pageable = PageRequest.of(page, 3, Sort.by("matchTime").ascending());

        return matchRepository.findAll(pageable);
    }

    public Page<Match> findMatchList(String dateKey, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber()-1);
        pageable = PageRequest.of(page, 3, Sort.by("matchTime").ascending());
        return matchRepository.findMatchList(dateKey, pageable);
    }
}
