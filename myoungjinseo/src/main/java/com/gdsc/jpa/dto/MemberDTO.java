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
@NoArgsConstructor      //  기본 생성자 생성
@AllArgsConstructor     //전체 변수를 생성해주는 생성자를 만들어준다.
public class MemberDTO {
    private Long id;

    @NotBlank       // @NotNull = null 만  허용x @NotEmpty = null + "" 허용x @NotBlack = null + "" + " " 허용 x
    @Size(max = 150)    //문자열, 배열 등 크기를 검증할 때 사용 (150이므로 150자 이하)
    private String name;

    @NotBlank
    private Integer age;

    private LocalDateTime createDate;

    private LocalDateTime lastModifiedDate;
}
