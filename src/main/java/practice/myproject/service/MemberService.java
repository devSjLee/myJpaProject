package practice.myproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
            log.info(username+"님이 로그인 시도");
            Optional<Member> findMember = memberRepository.findByLoginId(username);
            findMember.orElseThrow(() -> new UsernameNotFoundException("해당회원이 존재하지 않습니다."));
            return new CustomDetails(findMember.get());
    }
}
