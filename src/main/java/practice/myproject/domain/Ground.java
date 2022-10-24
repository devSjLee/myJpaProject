package practice.myproject.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Ground {

    @Id
    @GeneratedValue
    @Column(name = "ground_id")
    private Long id;

    private Double x;   //x좌표
    private Double y;   //y좌표

    private String address; //소재지 도로명주소
    private String groundName; //풋살장 이름
    private String callNumber; //사용안내 전화번호
    private String placeUrl;     //매칭장소 url주소
    private String groundKey;     // ground 고유 number

    private String showerYn;   //샤워시설 여부
    private String sportsWearYn;   //운동복대여 여부
    private String shoesYn;   //풋살화대여 여부
    private String parkingYn;   //주차가능 여부

    @OneToMany(mappedBy = "ground")
    private List<Match> matches = new ArrayList<>();

    @Builder
    public Ground(Long id, Double x, Double y, String address, String groundKey, String groundName, String callNumber, String placeUrl, String showerYn, String sportsWearYn, String shoesYn, String parkingYn, List<Match> matches) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.address = address;
        this.groundKey = groundKey;
        this.groundName = groundName;
        this.callNumber = callNumber;
        this.placeUrl = placeUrl;
        this.showerYn = showerYn;
        this.sportsWearYn = sportsWearYn;
        this.shoesYn = shoesYn;
        this.parkingYn = parkingYn;
        this.matches = matches;
    }
}
