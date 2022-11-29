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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name",nullable = false, length = 150)
    private String name;

    //1:N
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();

    public TeamDTO toDTO() {
        return TeamDTO.builder()
                .id(id)
                .name(name)
                .createDate(createDate)
                .lastModifiedDate(lastModifiedDate)
                .build();
    }

    // DTO객체 받아서 update
    public void update(TeamDTO dto){
        this.name = dto.getName();
    }
}
