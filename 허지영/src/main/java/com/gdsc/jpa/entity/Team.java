package com.gdsc.jpa.entity;

import com.gdsc.jpa.dto.TeamDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // Entity로 지정
@Table(name = "team") // 연결한 DB에서 team 테이블과 매핑
@Getter // OCP 개방폐쇄의 원칙
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team extends BaseTimeEntity{
    @Id // PK로 지정
    // 기본키 생성을 데이터베이스에 위임
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="name", nullable = false, length = 150) // not null 조건 생성, 길이 150
    private String name;

    // 1:n 관계 생성. 지연로딩, 부모가 자식 생명주기 관, 기존 Null 처리된 자식을 지워줌
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    public TeamDTO toDTO() {
        return TeamDTO.builder() // 가독성 좋게 객체 생성 => Builder
                .id(id)
                .name(name)
                .createDate(createDate)
                .lastModifiedDate(lastModifiedDate)
                .build();
    }

    public void update(TeamDTO dto){
        this.name=dto.getName();
    }
}
