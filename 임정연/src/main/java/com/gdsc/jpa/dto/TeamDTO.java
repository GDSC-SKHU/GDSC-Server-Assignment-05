package com.gdsc.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor //어노테이션은 모든 필드 값을 파라미터로 받는 생성자를 만들어줌
@NoArgsConstructor //어노테이션은 파라미터가 없는 기본 생성자를 생성
@Builder //클래스 레벨에 붙이거나 생성자에 붙여주면 파라미터를 활용하여 빌더 패턴을 자동으로 생성해줌
//붙이면 모든 요소를 받는 package-private 생성자가 자동으로 생성되며 이 생성자에 @Builder 어노테이션을 붙인 것과 동일하게 동작한다
public class TeamDTO {
    private Long id;

    @NotBlank //null이 아닌 값, 공백이 아닌 문자를 하나 이상 포함
    @Size(max = 100)
    private String name;

    @NotBlank //반드시 값이 있어야함
    private LocalDateTime createDatetime;
    private LocalDateTime lastModifiedDate;

}
/* DTO란?
-Data Transfer Object
-계층간 데이터 교환을 위한 객체이다
-DB에서 데이터를얻어 Service나 Controller 등으로 보낼 때 사용하는 객체
-toEntity()메서드를 통해 필요하나 부분을 이용하여 Entity로 만든다
-Controller Layer에서 ResponseDTO 형태로 Cㅣient에 전달한다
 */