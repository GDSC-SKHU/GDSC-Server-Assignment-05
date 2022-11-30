package com.gdsc.jpa.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDTO {
    private long id;

    @NotBlank
    @Size(max = 180)
    private String name;

    private LocalDateTime createDate;

    private LocalDateTime lastModifiedDate;

}
