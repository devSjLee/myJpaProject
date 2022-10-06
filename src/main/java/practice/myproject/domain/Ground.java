package practice.myproject.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Ground {

    @Id
    @GeneratedValue
    @Column(name = "ground_id")
    private Long id;

    private Double x;   //x좌표
    private Double y;   //y좌표

    private int square;     //면적
    private String address; //소재지 도로명주소
    private String groundName; //풋살장 이름
    private String callNumber; //사용안내 전화번호

    private String showerYn;   //샤워 여부
    private String sportsWearYn;   //운동복 여부
    private String shoesYn;   //풋살화 여부
    private String parkingYn;   //주차 여부

    @OneToOne(mappedBy = "ground")
    private Match match;





}
