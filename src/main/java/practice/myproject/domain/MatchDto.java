package practice.myproject.domain;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class MatchDto {

    @NotEmpty(message = "인원을 선택해주세요.")
    private String limitedPeople;  //제한 인원
    @NotEmpty(message = "매칭시간을 선택해주세요.")
    private String matchTime;     //매칭 시간

    private Double x;   //x좌표
    private Double y;   //y좌표

    private String placeUrl;     //매칭장소 url주소

    @NotEmpty(message = "장소를 입력해주세요.")
    private String address;     //매칭장소
    @NotEmpty(message = "구장명을 입력주세요.")
    private String groundName;      //구장명

    private String callNumber;      //연락처

    private String groundKey;   //ground 고유 number
}
