package practice.myproject.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.myproject.domain.*;
import practice.myproject.repository.GroundRepository;
import practice.myproject.repository.MatchRepository;
import practice.myproject.repository.MemberRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
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

        QMatch match2 = QMatch.match;

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        //기존 ground 테이블에 있는지 확인
        Integer result = queryFactory
                .selectOne()
                .from(match2)
                .where(match2.ground.groundKey.eq(matchDto.getGroundKey()))
                .fetchFirst();

        if (result == null) {
            Ground ground = Ground.builder()
                    .x(matchDto.getX())
                    .y(matchDto.getY())
                    .address(matchDto.getAddress())
                    .placeUrl(matchDto.getPlaceUrl())
                    .groundName(matchDto.getGroundName())
                    .callNumber(matchDto.getCallNumber())
                    .showerYn("Y")
                    .shoesYn("Y")
                    .sportsWearYn("Y")
                    .parkingYn("Y")
                    .groundKey(matchDto.getGroundKey())
                    .build();

            Ground saveGround = groundRepository.save(ground);

            Match match = Match.builder()
                    .ground(saveGround)
                    .limitedPeople(matchDto.getLimitedPeople())
                    .matchTime(matchDto.getMatchTime())
                    .createBy(loginId)
                    .createTime(LocalDateTime.now())
                    .build();
            Match match1 = matchRepository.save(match);
            return match1;
        } else {
            QGround ground = QGround.ground;

            Ground ground1 = queryFactory
                    .selectFrom(ground)
                    .where(ground.groundKey.eq(matchDto.getGroundKey()))
                    .fetchOne();

            Match match = Match.builder()
                    .ground(ground1)
                    .limitedPeople(matchDto.getLimitedPeople())
                    .matchTime(matchDto.getMatchTime())
                    .createBy(loginId)
                    .createTime(LocalDateTime.now())
                    .build();
            Match match1 = matchRepository.save(match);
            return match1;
        }

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
                .set(ground.placeUrl, matchDto.getPlaceUrl())
                .set(ground.x, matchDto.getX())
                .set(ground.y, matchDto.getY())
                .where(ground.id.eq(groundId))
                .execute();

        QMatch match1 = QMatch.match;
        JPAUpdateClause jpaUpdateClause = new JPAUpdateClause(em, match1);
        long execute = jpaUpdateClause
                .set(match1.matchTime, matchDto.getMatchTime())
                .set(match1.limitedPeople, matchDto.getLimitedPeople())
                .where(match1.id.eq(id))
                .execute();

        em.flush();
        em.clear();
        return execute;

    }


    public Page<Match> findMatchList2(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 3, Sort.by("matchTime").ascending());

        return matchRepository.findAll(pageable);
    }

    QMatch matchDynamic = QMatch.match;     //날짜, 키워드 검색용 Q-type match

    public Page<Match> findMatchList(String dateKey, String keyWord, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 7, Sort.by("matchTime").ascending());

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        List<Match> content = queryFactory
                .selectFrom(matchDynamic)
                .where(dateLike(dateKey), searchLike(keyWord))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(matchDynamic.matchTime.asc())
                .fetch();

        Long total = queryFactory
                .select(matchDynamic.count())
                .from(matchDynamic)
                .where(dateLike(dateKey), searchLike(keyWord))
                .fetchOne();


        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression searchLike(String keyWord) {
        if (keyWord == null) {
            return null;
        }
        return matchDynamic.ground.address.contains(keyWord);
    }

    private BooleanExpression dateLike(String dateKey) {
        if (dateKey == null) {
            return null;
        }
        return matchDynamic.matchTime.contains(dateKey);
    }
}
