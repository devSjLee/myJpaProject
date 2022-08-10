package practice.myproject.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MatchDto {

    private String title;    //매칭 제목
    private int limitedPeople;  //제한 인원
    private String matchAddress;   //매칭 장소
    private String matchTime;     //매칭 시간
    private String notice;  //공지사항, 한마디
}
