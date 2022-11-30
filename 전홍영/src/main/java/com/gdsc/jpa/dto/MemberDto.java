package com.gdsc.jpa.dto;

import com.gdsc.jpa.entity.Team;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter//getter 메소드 자동 생성
@AllArgsConstructor//생성자(파라미터로 field를 모두 포함) 자동 생성
@NoArgsConstructor//파라미터가 없는 생성자 자동 생성
@Builder//전체 인자를 갖는 생성자를 자동 생성
public class MemberDto {

    private Long id;//id
    @NotBlank//@NotBlank는 null과 공백을 허용하지 않는다.
    @Size(max = 100)
    private String name;//100이하 길이의 이름 100을 넘어가면 오류발생
    @NotBlank
    private Integer age;//나이

    private LocalDateTime createDate;

    private LocalDateTime lastModifiedDate;
}
