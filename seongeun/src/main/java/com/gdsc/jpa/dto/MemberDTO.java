package com.gdsc.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
@Getter
@Builder //전체 인자를 갖는 생성자를 만들어주는 어노테이션

@NoArgsConstructor // 기본 생성자 생성해주는 어노테이션
@AllArgsConstructor // 모든 필드 값을 받는 생성자를 만들어주는 어노테이션
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
