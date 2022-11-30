package com.gdsc.jpa.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDto {

    private Long id;
    @NotBlank
    @Size(max = 100)
    private String name;


    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
}
