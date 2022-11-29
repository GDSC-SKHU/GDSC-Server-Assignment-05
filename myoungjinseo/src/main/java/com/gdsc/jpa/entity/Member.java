package com.gdsc.jpa.entity;

import com.gdsc.jpa.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member  extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // DB 를 자동으로 AUTO_INCREMENT 를 하여 기본키를 생성해준다.
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "age", nullable = false)
    private int  age;

    @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY)   //  다대일
    @JoinColumn(name = "team_id")
    private Team team;

    public MemberDTO toDto() {
        return MemberDTO.builder()
                .id(id)
                .name(name)
                .age(age)
                .createDate(createDate)
                .lastModifiedDate(lastModifiedDate)
                .build();
    }

    public void update(MemberDTO dto) {     // MemberDTO 객체를 업데이트함(name, age)
        this.name = dto.getName();
        this.age = dto.getAge();
    }
}
