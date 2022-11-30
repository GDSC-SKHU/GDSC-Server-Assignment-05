package com.gdsc.gdscjpaexample.entity;

import com.gdsc.gdscjpaexample.dto.TeamDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
   Team 테이블을 객체화 시켰다고 생각하면 편함, entity
   실제 DB의 테이블과 매칭될 클래스

   id (PK)
   name
   createDate (BaseTimeEntity 상속)
   lastModifiedDate (BaseTimeEntity 상속)

   관계
   1:N (팀 : 멤버) 관계, 팀 하나에 여러 멤버가 소속, 한 멤버는 여러 팀에 소속 불가

   public TeamDTO toDTO()
   -> Team 객체 DTO 형태로 만들어 줌
   public void update(TeamDTO dto)
   -> 매개변수로 받은 dto 기반으로 현재 name update
*/

@Entity
@Table(name = "team")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team extends BaseTimeEntity { // BaseTimeEntity 상속받고 2개 속성 포함으로 테이블 구성

    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) // null 불가능
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    // 1:N (팀 : 멤버) 관계 설정
    @OneToMany(targetEntity = Member.class, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    // Team 객체 DTO 형태로 만들어 줌
    public TeamDTO toDTO() {
        return TeamDTO.builder()
                .id(id)
                .name(name)
                .createDate(createDate)
                .lastModifiedDate(lastModifiedDate)
                .build();
    }

    // 매개변수로 받은 dto 기반으로 현재 team 객체 update
    public void update(TeamDTO dto) {
        this.name = dto.getName();
    }
}
