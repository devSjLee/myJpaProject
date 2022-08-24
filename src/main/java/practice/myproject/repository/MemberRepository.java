package practice.myproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import practice.myproject.domain.Member;
import practice.myproject.domain.MemberLoginDto;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByLoginId(String loginId);

    Optional<Member> findByLoginId(String loginId);

}
