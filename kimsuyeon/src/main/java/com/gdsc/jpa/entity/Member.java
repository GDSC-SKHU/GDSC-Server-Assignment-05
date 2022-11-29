package com.gdsc.jpa.entity;


import com.gdsc.jpa.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="member")
@Getter
@NoArgsConstructor//파라미터가 없는 기본 생성자를 생성, 초기값 세팅이 필요한 final 변수가 있을 경우 컴파일 에러가 발생함
@AllArgsConstructor //전체 변수를 생성하는 생성자를 만들어줌
@Builder //@Builder를 사용 시 @AllArgsConstructor 어노테이션을 붙인 효과를 발생시켜 모든 멤버 필드에 대해서 매개변수를 받는 기본 생성자를 만든다.
public class Member extends BaseTimeEntity{

    @Id//해당 테이블의 PK 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY)//PK의 생성 규칙 표시
    @Column(name="id",nullable = false)//테이블의 칼럼임을 표시
    private Long id;

    @Column(name="name",nullable = false,length = 100)
    private String name;

    @Column(name="age",nullable = false,length = 100)
    private int age;

    @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY)//N:1 관계
    @JoinColumn(name="team_id")//외래 키를 매핑할 때 사용, 회원과 팀 테이블은 TEAM_ID 외래 키로 연관관계를 맺으므로 이 값을 지정
    private Team team;

    public MemberDto toDTO() { //Member를 DTO로 만들어줌
        return MemberDto.builder()//Builder를 사용하면 멤버 변수가 많아지더라도 어떤 값을 어떤 필드에 넣는지 코드를 통해 확인할 수 있고,
                                   // 필요한 값만 넣는 것이 가능

                .id(id)
                .name(name)
                .age(age)
                .createDate(createDate)
                .lastModifiedDate(lastModifiedDate)
                .build();
    }

    public void update(MemberDto dto) { //MemberDto 객체를 인자로 받아 Member 객체의 값을 변경
        this.name = dto.getName();
        this.age = dto.getAge();
    }
}

/*
member와 team과는 1대다로 연결
team이 1 member가 다
 */