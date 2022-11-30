package com.gdsc.jpa.entity;

import com.gdsc.jpa.dto.MemberDto;
import com.gdsc.jpa.dto.TeamDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity//Entity 객체
@Table(name = "member")//테이블 이름
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {
    @Id//PK 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)//PK 생성전략(Auto_increment를 사용)
    @Column(name = "id", nullable = false)//컬럼 설정 ,null 불가능
    private Long id;

    @Column(name = "name", nullable = false, length = 150)//컬럼 설정 ,null 불가능, 최대 길이 150
    private String name;

    @Column(name = "age", nullable = false)//컬럼 설정 ,null 불가능
    private Integer age;

    @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY)//여러 Member가 한 Team에 사용될수 있으므로 @ManyToOne 추가
    @JoinColumn(name = "team_id")//team_id로 Join
    private Team team;

    //Memeber를 Dto로 바꾸는 메소드
    public MemberDto toDto() {
        return MemberDto.builder()
                .id(id)
                .name(name)
                .age(age)
                .createDate(createDate)
                .lastModifiedDate(lastModifiedDate)
                .build();
    }
    //update 메소드
    public void update(MemberDto memberDto) {
        this.name = toDto().getName();
        this.age = toDto().getAge();
    }
}
