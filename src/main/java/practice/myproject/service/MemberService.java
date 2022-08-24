package practice.myproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import practice.myproject.controller.CustomDetails;
import practice.myproject.domain.*;
import practice.myproject.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username = " + username);
        Optional<Member> findMember = memberRepository.findByLoginId(username);
        if(findMember != null) {
            System.out.println("널아님");
            Member member = findMember.get();
            return new CustomDetails(member);
        }
        return null;
    }
}
