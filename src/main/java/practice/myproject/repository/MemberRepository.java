package practice.myproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.myproject.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByLoginId(String loginId);
}
