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
public class Member extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "name", nullable = false,length = 150)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @ManyToOne(targetEntity = Team.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;


    public MemberDTO toDTO(){
        return MemberDTO.builder()
                .id(id)
                .name(name)
                .age(age)
                .createDate(createDate)
                .lastModifiedDate(lastModifiedDate)
                .build();
    }

    public void update(MemberDTO dto) {
        if(dto.getName() !=null)
            this.name = dto.getName();
        if(dto.getAge() != null)
            this.age = dto.getAge();
    }
}
