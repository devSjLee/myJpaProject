package practice.myproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import practice.myproject.domain.Member;
import practice.myproject.domain.MemberDto;
import practice.myproject.domain.MemberGrade;
import practice.myproject.domain.MemberLoginDto;
import practice.myproject.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;

    public void save(MemberDto memberDto) {
        Member member = Member.builder()
                .name(memberDto.getName())
                .loginId(memberDto.getLoginId())
                .password(encoder.encode(memberDto.getPassword()))
                .address(memberDto.getAddress())
                .addressDetail(memberDto.getAddressDetail())
                .email(memberDto.getEmail())
                .grade(MemberGrade.PRIMARY)
                .nickName(memberDto.getNickName())
                .phone(memberDto.getPhone())
                .zipcode(memberDto.getZipcode())
                .build();
        memberRepository.save(member);
    }

    public Boolean checkDuplicatedId(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }


}
