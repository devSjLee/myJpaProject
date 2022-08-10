package practice.myproject.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Match {

    @Id
    @GeneratedValue
    @Column(name = "match_id")
    private Long id;

    private String title;    //매칭 제목
    private int limitedPeople;  //제한 인원
    private String place;   //매칭 장소
    private LocalDateTime time;     //매칭 시간
    private String notice;  //공지사항, 한마디

    private LocalDateTime createTime;   //글 작성 시간

    @OneToMany(mappedBy = "match")
    private List<Member> members = new ArrayList<>();
}
