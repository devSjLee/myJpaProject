package practice.myproject.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Meeting {

    @Id
    @GeneratedValue
    @Column(name = "meeting_id")
    private Long id;

    private String meetName;    //모임명
    private int maxPeople;  //정원수
    private String purpose; //모임 목표


    @OneToMany(mappedBy = "meeting")
    private List<Member> members = new ArrayList<>();
}
