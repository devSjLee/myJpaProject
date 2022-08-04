package practice.myproject.domain;

import lombok.Builder;
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

    private String loginId;
    private String password;

    private String name;    //이름
    private String nickName;    //닉네임

    private String phone;   //휴대폰 앞

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


    @Builder
    public Member(Long id, String loginId, String password, String name, String nickName, String phone, String email, String zipcode, String address, String addressDetail, MemberGrade grade, Meeting meeting) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.phone = phone;
        this.email = email;
        this.zipcode = zipcode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.grade = grade;
        this.meeting = meeting;
    }
}
