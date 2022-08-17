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

    private String title;    //매칭 제목
    private int limitedPeople;  //제한 인원
    private String matchAddress;   //매칭 장소
    private LocalDateTime matchTime;     //매칭 시간
    private String notice;  //공지사항, 한마디

    private LocalDateTime createTime;   //글 작성 시간

    @OneToMany(mappedBy = "match")
    private List<Member> members = new ArrayList<>();

    @Builder
    public Match(Long id, String title, int limitedPeople, String matchAddress, LocalDateTime matchTime, String notice, LocalDateTime createTime, List<Member> members) {
        this.id = id;
        this.title = title;
        this.limitedPeople = limitedPeople;
        this.matchAddress = matchAddress;
        this.matchTime = matchTime;
        this.notice = notice;
        this.createTime = createTime;
        this.members = members;
    }
}
