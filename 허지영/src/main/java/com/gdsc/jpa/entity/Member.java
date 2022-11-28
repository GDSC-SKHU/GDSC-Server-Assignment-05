package com.gdsc.jpa.entity;

import com.gdsc.jpa.dto.MemberDTO;
import lombok.*;

import javax.persistence.*;

@Getter // ocp 계방폐쇄원칙
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자를 막아줌(무분별한 객체 생성 막아줌)
@AllArgsConstructor
// Entity로 지정
@Entity
// 연결한 DB에서 member라는 테이블이랑 매핑
@Table(name = "member")
@Builder
public class Member extends BaseTimeEntity {

    @Id // PK
    // 기본키 생성을 데이터베이스에 위임
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 해당 필드 DDL 생성시 notnull 조건 생성
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 150) // notnull 조건 생성, 길이 150
    private String name;

    @Column(name = "age", nullable = false) // not null 조건 생성
    private int age;

    @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY) // n:1 관계, 지연로딩
    @JoinColumn(name = "team_id") // 테이블 조인
    private Team team;

    public MemberDTO toDTO() {
        return MemberDTO.builder() // 파라미터를 활용하여 빌더 패턴을 자동으로 생성
                .id(id)
                .name(name)
                .age(age)
                .createDate(createDate)
                .lastModifiedDate(lastModifiedDate)
                .build();
    }

    public void update(MemberDTO dto) { // 업데이트 메소드 생성(이름과 나이 설정)
        this.name = dto.getName();
        this.age = dto.getAge();
    }
}