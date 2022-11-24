package com.gdsc.gdscjpaexample.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/*
   DTO(Data Transfer Object)
   계층간 데이터 교환을 위한 객체
   DB에서 데이터를 얻어 Service나 Controller로 보낼 때 사용

   id
   name
   createDate
   lastModifiedDate
*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDTO {
    private Long id;

    @NotBlank
    @Size(max = 150)
    private String name;

    private LocalDateTime createDate;

    private LocalDateTime lastModifiedDate;
}
