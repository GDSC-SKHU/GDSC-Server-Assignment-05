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
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT 와 같은 역할.
    @Column(name = "id", nullable = false) //id column 생성
    private Long id;

    @Column(name = "name", nullable = false, length = 100) //name column 생성
    private String name;

    @Column(name = "age", nullable = false) //age column 생성
    private Integer age;

    @ManyToOne(targetEntity = Team.class, fetch = FetchType.LAZY) //lazy를 실무에서 더 많이 사용
    @JoinColumn(name = "team_id") //team에서 id를 join하고 이름은 team_id로 함.
    private Team team;

    public MemberDTO toDTO() { //이처럼 빌더패턴 사용 시 가독성을 높일 수 있음.
        return MemberDTO.builder().id(id).age(age).name(name).createDate(createDate).lastModifiedDate(lastModifiedDate).build();
    }

    public void update(MemberDTO dto) {
        this.name = dto.getName();
        this.age = dto.getAge();
    }
}
