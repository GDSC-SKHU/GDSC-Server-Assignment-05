package com.gdsc.jpa.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter // 읽기 전용
@Builder // 데이터 일관성을 위해 정보들을 다 받은 후에 객체를 생성
@NoArgsConstructor // 파라미터가 없는 생성자를 생성
@AllArgsConstructor // 클래스에 존재하는 모든 필드에 대한 생성자를 자동으로 생성
public class MemberDTO {
    private Long id; // 멤버 id

    @NotBlank // Null만 허용 안 함 -> "" 이나 " " 은 허용
    @Size(max = 150) // 연결된 문자열의 길이가 min/max에 맞게 유효한지 확인 -> 여기선 멤버 이름 길이가 최대 150인지 확인
    private String name; // 멤버 이름

    @NotBlank
    private Integer age; // 멤버 나이

    private LocalDateTime createDate; // 생성 시간
    private LocalDateTime lastModifiedDate; // 마지막 수정 시간
}