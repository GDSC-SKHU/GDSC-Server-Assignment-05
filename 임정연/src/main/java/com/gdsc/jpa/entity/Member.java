package com.gdsc.jpa.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.gdsc.jpa.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity //db의 테이블과 일대일로 매칭되는 객체 단위이며 Entity객체의 인스턴스 하나가 테이블에서 하나의 레코드 값을 의미
@Table(name = "member") //db상의 실제 테이블 명칭을 지정
@Getter
@AllArgsConstructor //어노테이션은 모든 필드 값을 파라미터로 받는 생성자를 만들어줌
@NoArgsConstructor // 어노테이션은 파라미터가 없는 기본 생성자를 생성
@Builder  //클래스 레벨에 붙이거나 생성자에 붙여주면 파라미터를 활용하여 빌더 패턴을 자동으로 생성해줌
//붙이면 모든 요소를 받는 package-private 생성자가 자동으로 생성되며 이 생성자에 @Builder 어노테이션을 붙인 것과 동일하게 동작한다
public class Member extends BaseTimeEntity {  //BaseTimeEntity 상속
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    @Id
    private Long id;
    @Column(name="name",nullable = false,length=100)
    private String name;

    @Column(name="age",nullable = false)
    private Integer age;

    @ManyToOne(targetEntity = Team.class, fetch=FetchType.LAZY) //1:N 팀이 N
    @JoinColumn(name="team_id")
    private Team team;

    public MemberDTO toDTO(){
        return MemberDTO.builder()
                .id(id)
                .name(name)
                .createDate(createDate)
                .lastModifiedDate(lastModifiedDate)
                .build();
    }

    public void update(MemberDTO mdto){
        this.name=mdto.getName();
    }

}
