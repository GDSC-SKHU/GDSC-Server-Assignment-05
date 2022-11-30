package com.gdsc.jpa.entity;

import com.gdsc.jpa.dto.TeamDTO;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity                     // 객체와 DB 테이블 매핑, JPA가 관리, 기본 생성자 필수
@Table(name = "team")       // team이라는 이름의 테이블과 매핑
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)          // 파라미터가 없는 기본 생성자 자동 생성
@AllArgsConstructor         // 모든 필드 값을 파라미터로 받는 생성자 생성
/*
빌더 패턴 - 객체를 생성하는 별도 builder를 반환
생성자에서 인자가 많으면 가독성이 좋지 않다. 빌더 패턴으로 구현하면 빌더의 이름 함수로 각 값들을 셋팅하여 무슨 값을 의미하는지 파악하기 쉽다.
순서에 구애받지 않아 필요한 데이터만 설정할 수 있다.
*/
@Builder                    // 빌더 패턴 - 객체를 생성하는 별도 builder를 반환

public class Team extends BaseTimeEntity{       // 공통 속성 상속

    @Id             // 테이블의 기본 키(PK)와 객체의 필드를 매핑
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // 기본키를 직접 할당하는 대신 데이터베이스에 위임
    @Column(name="id", nullable = false)        // name="객체와 매핑할 테이블의 컬럼 이름", not null
    private Long id;

    @Column(name="name", nullable = false, length = 150)
    private String name;

    /*
     팀을 기준으로 팀 -> 회원은 1:N 관계
     mappedBy - 객체간 양방향 연결시 관계의 주체가 되는 쪽에서 정의
     fetch(데이터 읽기 전략) - AGER: 관계된 Entity의 정보를 미리 읽어옴(즉시로딩), LAZY: 실제로 요청하는 순간 가져옴(지연로딩-지향)
     cascade(부모 Entity의 변경에 대해 관계를 맺은 자식 Entity의 변경 전략) - PERSIST: 같이 변경, REMOVE: 같이 삭제, ALL: 모든 CASCADE 옵션 적용
     orphanRemoval - 관계 Entity에서 변경이 일어난 경우 DB 변경을 같이 할지 결정. cascade는 JPA 레이어 수준이고 orphanRemoval는 DB레이어 수준. 기본은 false
     */
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    final private List<Member> members = new ArrayList<>();      // 멤버 목록을 ArrayList(크기가 가변적)로 받아옴

    // 빌더를 사용해 TeamDTO 객체 생성
    public TeamDTO toDTO() {
        return TeamDTO.builder()
                .id(id)
                .name(name)
                .createDate(createDate)
                .lastModifiedDate(lastModifiedDate)
                .build();
    }

    //Shift 키를 두 번 누르면 모든 항목을 검색할 수 있는 검색 상자 열림
    // TeamDTO 객체를 받아 update
    public void update(TeamDTO dto){
        this.name = dto.getName();
    }
}
