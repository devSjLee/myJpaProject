package practice.myproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.myproject.repository.MatchRepository;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
}
