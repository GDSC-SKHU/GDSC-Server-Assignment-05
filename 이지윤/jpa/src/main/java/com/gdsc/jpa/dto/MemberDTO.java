package com.gdsc.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor //파라미터가 없는 기본 생성자를 생성
@AllArgsConstructor //모든 필드 값을 파라미터로 받는 생성자를 만듦
public class MemberDTO {

    private Long id;

    @NotBlank
    @Size(max = 150)
    private String name;

    @NotBlank
    private Integer age;

    private LocalDateTime createDate;

    private LocalDateTime lastModifiedDate;
}

