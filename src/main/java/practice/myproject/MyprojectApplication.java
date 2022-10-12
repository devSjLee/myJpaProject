package practice.myproject;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import practice.myproject.domain.Match;
import practice.myproject.repository.MatchRepository;
import practice.myproject.service.MatchService;

import java.util.List;

@SpringBootApplication
@EnableScheduling
@RequiredArgsConstructor
public class MyprojectApplication {

    private final MatchService matchService;
    private final MatchRepository matchRepository;

    public static void main(String[] args) {
        SpringApplication.run(MyprojectApplication.class, args);
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Scheduled(cron = "0 0 * * * *")
    public void batch() {    //매일 00:00에 오늘날짜기준 매칭타임이 지난 매칭들 삭제처리하는 배치
        List<Match> deleteMatch = matchRepository.findDeleteMatch();
        for (Match match : deleteMatch) {
            matchService.deleteMatch(match.getId());
        }
    }

}
