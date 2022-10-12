package practice.myproject.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private String showerYn;   //샤워시설 여부
    private String sportsWearYn;   //운동복대여 여부
    private String shoesYn;   //풋살화대여 여부
    private String parkingYn;   //주차가능 여부

    @OneToOne(mappedBy = "ground")
    private Match match;

    @Builder
    public Ground(Long id, Double x, Double y, String address, String groundName, String callNumber, String showerYn, String sportsWearYn, String shoesYn, String parkingYn, Match match) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.address = address;
        this.groundName = groundName;
        this.callNumber = callNumber;
        this.showerYn = showerYn;
        this.sportsWearYn = sportsWearYn;
        this.shoesYn = shoesYn;
        this.parkingYn = parkingYn;
        this.match = match;
    }
}
