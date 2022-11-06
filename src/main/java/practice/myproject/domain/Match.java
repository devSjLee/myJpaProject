package practice.myproject.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Match {

    @Id
    @GeneratedValue
    @Column(name = "match_id")
    private Long id;

    private String limitedPeople;  //제한 인원

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ground_id")
    private Ground ground;   //매칭 장소

    private String matchTime;     //매칭 시간

    private String createBy;    //작성자
    private LocalDateTime createTime;   //글 작성 시간

    @OneToMany(mappedBy = "match")
    private List<Member> members = new ArrayList<>();

    @Builder
    public Match(Long id, String limitedPeople, Ground ground, String matchTime, String createBy, LocalDateTime createTime, List<Member> members) {
        this.id = id;
        this.limitedPeople = limitedPeople;
        this.ground = ground;
        this.matchTime = matchTime;
        this.createBy = createBy;
        this.createTime = createTime;
        this.members = members;
    }
}
