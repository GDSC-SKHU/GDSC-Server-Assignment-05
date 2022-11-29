package com.gdsc.jpa.entity;

import com.gdsc.jpa.dto.TeamDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // 테이블과의 매핑
@Table(name = "team") // 연결한 DB에서 team라는 테이블이랑 매핑
@Getter // Getter를 지향하고 Setter를 지양하자.(OCP: 개방폐쇄의 원칙)
@NoArgsConstructor // 파라미터가 없는 생성자를 생성
@AllArgsConstructor // 클래스에 존재하는 모든 필드에 대한 생성자를 자동으로 생성
@Builder // 데이터 일관성을 위해 정보들을 다 받은 후에 객체를 생성
public class Team extends BaseTimeEntity {
    @Id // 기본키(PK)로 지정
    // IDENTITY : 기본 키 생성을 데이터베이스에 위임 (= AUTO_INCREMENT)
    // Statement.getGeneratedKeys() 를 사용해서 데이터를 저장함과 동시에 생성된 기본 키 값을 얻어 올 수 있음
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column : 객체 필드를 테이블 컬럼에 매핑
    // nullable (DDL) : null 값의 허용 여부 설정, false 설정 시 not null (default. true)
    // @Column 사용 시 nullable = false 로 설정하는 것이 안전
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    // CascadeType.PERSIST : 부모가 자식의 전체 생명 주기를 관리
    // orphanRemoval = true : 부모 엔티티가 삭제되면 자식 엔티티도 삭제
    // 부모 엔티티가 자식 엔티티의 관계를 제거하면 자식은 고아로 취급되어 그대로 사라짐
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    // Tema 객체를 DTO로 만듦
    public TeamDTO toDTO() {
        // Builder를 이용해서 가독성이 좋게 객체를 생성할 수 있다.
        return TeamDTO.builder()
                .id(id)
                .name(name)
                .createDate(createDate)
                .lastModifiedDate(lastModifiedDate)
                .build();
    }

    // TeamDto 객체를 인자로 받아 Team 객체의 값을 변경
    public void update(TeamDTO dto) {
        this.name = dto.getName();
    }
}