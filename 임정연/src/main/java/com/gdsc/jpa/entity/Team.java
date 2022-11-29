package com.gdsc.jpa.entity;


import com.gdsc.jpa.dto.TeamDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity //db의 테이블과 일대일로 매칭되는 객체 단위이며 Entity객체의 인스턴스 하나가 테이블에서 하나의 레코드 값을 의미
@Table(name = "team") //db상의 실제 테이블 명칭을 지정
@Getter
@AllArgsConstructor //어노테이션은 모든 필드 값을 파라미터로 받는 생성자를 만들어줌
@NoArgsConstructor // 어노테이션은 파라미터가 없는 기본 생성자를 생성
@Builder  //클래스 레벨에 붙이거나 생성자에 붙여주면 파라미터를 활용하여 빌더 패턴을 자동으로 생성해줌
//붙이면 모든 요소를 받는 package-private 생성자가 자동으로 생성되며 이 생성자에 @Builder 어노테이션을 붙인 것과 동일하게 동작한다
public class Team extends BaseTimeEntity{ //BaseTimeEntity 상속
    @Id //테이블 상의 PrimaryKey와 같은 의미를 가졌음
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    /*
    PK 컬럼의 데이터 형식은 정해져 있지는 않으나 구분이 가능한 유일한 값을 가지고 있어야 하고
    데이터 결합으로 인해 발생되는 데드락 같은 현상을 방지하기 위해 사용
    MySql의 auto increment 방식 - 새로운 레코드가 생성 될때마다 마지막 PK 값에서 자동으로 +1
     */
    @Column(name="id", nullable = false) //null이면 안된다
    private Long id;

    @Column(name="name", nullable = false, length=500)//null이면 안된다
    private String name;

    //멤버와 팀은 1:N관계
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Member> member = new ArrayList<>();
    //remove 는 팀이 폭발해도 null, orphan은 팀이 폭발하면 멤버 전체 사망
    //fetchtype lazy (지연로딩) - 지연로딩을 사용하면 Team을 사용하는 시점에 쿼리가 나가도록 할 수 있다는 장점이 있음

    public TeamDTO toDTO(){ //팀을 toDTO()를 통해 dto형태로 만듦
        return TeamDTO.builder()
                .id(id)
                .name(name)
                .createDatetime(createDate)
                .lastModifiedDate(lastModifiedDate)
                .build();
    }
    public void update(TeamDTO dto){ //팀 객체 수정
        this.name = dto.getName();
    }
}

