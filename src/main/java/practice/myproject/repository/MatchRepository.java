package practice.myproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.myproject.domain.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
}
