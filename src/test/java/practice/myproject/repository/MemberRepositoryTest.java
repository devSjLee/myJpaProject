package practice.myproject.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import practice.myproject.domain.Member;
import practice.myproject.domain.MemberGrade;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    public void createMember() {
        Member member = new Member(123L, "이성준", "셔거덜","010","5452","3781","test@test.com", "12312", "서울시 영등포구", "스카이빌 102호", MemberGrade.ADMIN, null);
        memberRepository.save(member);


    }

}