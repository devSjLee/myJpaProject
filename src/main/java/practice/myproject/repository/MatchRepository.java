package practice.myproject.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import practice.myproject.domain.Match;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("select m from Match m where m.matchTime like %:dateKey%")
    Page<Match> findMatchList(@Param("dateKey") String dateKey, Pageable pageable);

    @Query("select m from Match m where FORMATDATETIME(m.matchTime, 'yyyy-MM-dd') < FORMATDATETIME(NOW(), 'yyyy-MM-dd')")
    List<Match> findDeleteMatch();
}
