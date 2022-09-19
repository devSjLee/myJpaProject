package practice.myproject.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MatchDto {

    private int limitedPeople;  //제한 인원
    private String matchAddress;   //매칭 장소
    private LocalDateTime matchTime;     //매칭 시간
    private String notice;  //공지사항, 한마디
}
