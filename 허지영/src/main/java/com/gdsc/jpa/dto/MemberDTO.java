package com.gdsc.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank; // gradle 빌드 후에 실행 가능
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter // ocp원칙
@Builder // 객체를 정의하고 객체를 생성해줌
@NoArgsConstructor // 파라미터가 없는 기본 생성자 생성
@AllArgsConstructor // 파랄미터가 모두 있는 생성자 생성
public class MemberDTO {
    private Long id;

    @NotBlank // 빈칸으로 지정 x
    @Size(max=150) // 크기는 최대 150자
    private String name;

    @NotBlank // 빈칸 지정 x
    private Integer age;

    private LocalDateTime createDate;

    private LocalDateTime lastModifiedDate;
}
