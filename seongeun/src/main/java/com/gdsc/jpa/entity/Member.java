package com.gdsc.jpa.entity;

import com.gdsc.jpa.dto.MemberDTO;
import lombok.*;

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
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name",nullable = false, length = 100)
    private String name;

    @Column(name = "age",nullable = false)
    private Integer age;

    //N:1
    @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id") // 외래키
    private Team team;

    public MemberDTO toDTO() {
        return MemberDTO.builder()
                .id(id)
                .name(name)
                .age(age)
                .createDate(createDate)
                .lastModifiedDate(lastModifiedDate)
                .build();
    }

    // DTO객체 받아서 update
    public void update(MemberDTO dto) {
        this.name = dto.getName();
        this.age = dto.getAge();
    }
}
