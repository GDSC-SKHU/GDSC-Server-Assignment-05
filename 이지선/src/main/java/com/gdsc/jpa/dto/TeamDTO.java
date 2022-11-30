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
@NoArgsConstructor
@AllArgsConstructor

// DTO(Date Transfer Object): DB에서 데이터를 얻어 Service나 Controller로 보낼 때 사용하는 객체
public class TeamDTO {
    private Long id;

    // @NotNull은 Null만 허용하지 않아 "", " "은 허용
    // @NotEmpty는 null,""은 허용하지 않고, " "은 허용
    // @NotBlank는 null 과 ""," " 모두 허용X - 가장 강한 validation
    @NotBlank
    @Size(max=150)
    private String name;

    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
}
