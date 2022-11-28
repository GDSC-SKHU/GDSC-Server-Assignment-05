package com.gdsc.jpa.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Builder //@Builder 애노테이션을 선언하면 전체 인자를 갖는 생성자를 자동으로 만듬.
@NoArgsConstructor//@NoArgsConstructor 어노테이션은 파라미터가 없는 기본 생성자를 생성
@AllArgsConstructor//@AllArgsConstructor 어노테이션은 모든 필드 값을 파라미터로 받는 생성자를 만들어줌
public class TeamDto {

    private Long id;

    @NotBlank// null, "", " " 모두 허용하지 않음
    @Size(max=100)//글자수 최대 100
    private String name;


    private LocalDateTime createDate;// 생성일
    private LocalDateTime lastModifiedDate;// 수정일

}
