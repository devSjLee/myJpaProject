package practice.myproject.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MatchDto {


    private int limitedPeople;  //제한 인원
    private String matchTime;     //매칭 시간
    private String notice;  //공지사항, 한마디

    private Double x;   //x좌표
    private Double y;   //y좌표

    private String placeUrl;     //매칭장소 url주소
    private String address;     //매칭장소
    private String groundName;      //구장명
    private String callNumber;      //연락처

    private String groundKey;   //ground 고유 number
}
