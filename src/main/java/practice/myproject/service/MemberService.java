package practice.myproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.myproject.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

}
