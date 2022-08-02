package practice.myproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.myproject.domain.Member;
import practice.myproject.domain.MemberDto;
import practice.myproject.domain.MemberGrade;
import practice.myproject.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void save(MemberDto memberDto) {
        Member member = Member.builder()
                .name(memberDto.getName())
                .loginId(memberDto.getLoginId())
                .password(memberDto.getPassword())
                .address(memberDto.getAddress())
                .addressDetail(memberDto.getAddressDetail())
                .email(memberDto.getEmail())
                .grade(MemberGrade.PRIMARY)
                .nickName(memberDto.getNickName())
                .ph11(memberDto.getPh11())
                .ph12(memberDto.getPh12())
                .ph13(memberDto.getPh13())
                .zipcode(memberDto.getZipcode())
                .build();
        memberRepository.save(member);
    }

}
