package practice.myproject.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
public class MemberDto {

    @NotEmpty(message = "아이디는 필수입니다.")
    private String loginId;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;

    @NotEmpty(message = "이름은 필수입니다.")
    private String name;    //이름

    @NotEmpty(message = "닉네임을 적어주세요")
    private String nickName;    //닉네임

    @NotEmpty(message = "휴대폰번호는 필수입니다.")
    private String ph11;   //휴대폰 앞
    @NotEmpty(message = "휴대폰번호는 필수입니다.")
    private String ph12;   //휴대폰 중간
    @NotEmpty(message = "휴대폰번호는 필수입니다.")
    private String ph13;   //휴대폰 끝

    @NotEmpty(message = "이메일은 필수입니다.")
    private String email;   //이메일
    @NotEmpty(message = "우편번호는 필수입니다.")
    private String zipcode;     //우편번호
    @NotEmpty(message = "주소는 필수입니다.")
    private String address;     //주소
    @NotEmpty(message = "상세주소는 필수입니다.")
    private String addressDetail;   //상세주소

}
