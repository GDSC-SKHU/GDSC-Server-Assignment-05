package com.gdsc.jpa.entity;

import com.gdsc.jpa.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity // 테이블과의 매핑
@Table(name = "member") // 연결한 DB에서 member라는 테이블이랑 매핑
@Getter // Getter를 지향하고 Setter를 지양하자.(OCP: 개방폐쇄의 원칙)
@NoArgsConstructor // 파라미터가 없는 생성자를 생성
@AllArgsConstructor // 클래스에 존재하는 모든 필드에 대한 생성자를 자동으로 생성
@Builder // 데이터 일관성을 위해 정보들을 다 받은 후에 객체를 생성
public class Member extends BaseTimeEntity {
    @Id // 기본키(PK)로 지정
    // IDENTITY : 기본 키 생성을 데이터베이스에 위임 (= AUTO_INCREMENT)
    // Statement.getGeneratedKeys() 를 사용해서 데이터를 저장함과 동시에 생성된 기본 키 값을 얻어 올 수 있음
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column : 객체 필드를 테이블 컬럼에 매핑
    // nullable (DDL) : null 값의 허용 여부 설정, false 설정 시 not null (default. true)
    // @Column 사용 시 nullable = false 로 설정하는 것이 안전
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    // 단방향 N:1 설정, 지연로딩(멤버에서 팀을 조회할 때 쿼리가 두번나감)
    // 그 밖에 즉시로딩은 멤버를 불러오는 동시에 팀을 불러오기때문에 쿼리가 적게나감
    // 상황에 맞게 지연로딩, 즉시로딩을 사용하고 되도록 지연로딩을 지향하자.
    @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY)
    // @JoinColumn : 외래키(FK)를 매핑할 때 사용 -> 즉, team_id은 매핑할 외래 키 이름
    @JoinColumn(name = "team_id")
    private Team team;

    // Member 객체를 DTO로 만듦
    public MemberDTO toDTO() {
        // Builder를 이용해서 가독성이 좋게 객체를 생성할 수 있음
        return MemberDTO.builder()
                .id(id)
                .name(name)
                .age(age)
                .createDate(createDate)
                .lastModifiedDate(lastModifiedDate)
                .build();
    }

    // MemberDto 객체를 인자로 받아 Member 객체의 값을 변경
    public void update(MemberDTO dto) {
        this.name = dto.getName();
        this.age = dto.getAge();
    }
}
