package com.gdsc.gdscjpaexample.entity;

import com.gdsc.gdscjpaexample.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
   Team 테이블을 객체화 시켰다고 생각하면 편함, entity
   실제 DB의 테이블과 매칭될 클래스

   id (PK)
   name
   age
   createDate (BaseTimeEntity 상속)
   lastModifiedDate (BaseTimeEntity 상속)

   관계
   N:1 (멤버 : 팀) 관계, 팀 하나에 여러 멤버가 소속, 한 멤버는 여러 팀에 소속 불가

   public MemberDTO toDTO()
   -> Member 객체 DTO 형태로 만들어 줌
   public void update(MemberDTO dto)
   -> 매개변수로 받은 dto 기반으로 현재 name, age update
*/

@Entity
@Table(name = "Member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity { // BaseTimeEntity 상속받고 2개 속성 포함으로 테이블 구성
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) // null 불가능
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    // N:1 (멤버 : 팀) 관계 설정
    @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id") // join 조건
    private Team team;

    // Member 객체 DTO 형태로 만들어 줌
    public MemberDTO toDTO() {
        return MemberDTO.builder()
                .id(id)
                .name(name)
                .age(age)
                .createDate(createDate)
                .lastModifiedDate(lastModifiedDate)
                .build();
    }

    // 매개변수로 받은 dto 기반으로 현재 member 객체 update
    public void update(MemberDTO dto) {
        this.name = dto.getName();
        this.age = dto.getAge();
    }
}
