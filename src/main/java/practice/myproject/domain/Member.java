package practice.myproject.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;    //이름
    private String nickName;    //닉네임

    private String ph11;   //휴대폰 앞
    private String ph12;   //휴대폰 중간
    private String ph13;   //휴대폰 끝

    private String email;   //이메일
    private String zipcode;     //우편번호
    private String address;     //주소
    private String addressDetail;   //상세주소

    @Enumerated(EnumType.STRING)
    @Column(name = "grade")
    private MemberGrade grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    public Member(Long id, String name, String nickName, String ph11, String ph12, String ph13, String email, String zipcode, String address, String addressDetail, MemberGrade grade, Meeting meeting) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.ph11 = ph11;
        this.ph12 = ph12;
        this.ph13 = ph13;
        this.email = email;
        this.zipcode = zipcode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.grade = grade;
        this.meeting = meeting;
    }
}
