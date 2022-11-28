package com.gdsc.jpa.entity;

import com.gdsc.jpa.dto.TeamDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "team")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team extends BaseTimeEntity{

    // 자동으로 Id 값이 1부터 채워진다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;


    // fetch = FetchType.LAZY => 지연로딩으로 설정
    // cascade = CascadeType.PERSIST => 엔티티를 영속화할 때, 연관된 엔티티도 함께 유지
    // orphanRemoval = true => 부모 엔티티 삭제 -> 자식 엔티티도 삭제 , 부모 엔티티에서 자식 엔티티 제거 -> 자식엔티티를 제거 , 유사) CascadeType.REMOVE
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    public TeamDTO toDto(){
        return TeamDTO.builder()
                .id(id)
                .name(name)
                .createDate(createDate)
                .lastModifiedDate(lastModifiedDate)
                .build();
    }

    public void update(TeamDTO dto){
        this.name = dto.getName();
    }
}
