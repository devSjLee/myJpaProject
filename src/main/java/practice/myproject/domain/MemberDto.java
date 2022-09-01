package practice.myproject.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class MemberDto {

    @NotEmpty(message = "아이디는 필수입니다.")
    @Size(min = 4, max = 15, message = "아이디는 4~15자리 사이로 입력해주세요")
    @Pattern(regexp = "^[A-Za-z]+[0-9]+$", message = "영문, 숫자 혼용하여 입력해주세요")
    private String loginId;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    @Size(min = 4, max = 15, message = "비밀번호는 4~15자리 사이로 입력해주세요")
    private String password;

    @NotEmpty(message = "이름은 필수입니다.")
    @Pattern(regexp = "^[A-Za-z가-힣]+$", message = "영문 대소문자, 한글")
    private String name;    //이름

    @NotEmpty(message = "닉네임을 적어주세요")
    @Pattern(regexp = "^[가-힣]+$", message = "닉네임은 한글만")
    private String nickName;    //닉네임

    @NotEmpty(message = "휴대폰번호는 필수입니다.")
    @Pattern(regexp = "^[0-9]+$", message = "휴대폰번호는 숫자만")
    private String phone;   //휴대폰 앞

    @NotEmpty(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;   //이메일
    @NotEmpty(message = "우편번호는 필수입니다.")
    private String zipcode;     //우편번호
    @NotEmpty(message = "주소는 필수입니다.")
    private String address;     //주소
    @NotEmpty(message = "상세주소는 필수입니다.")
    private String addressDetail;   //상세주소

}
