package com.gdsc.jpa.entity;

import com.gdsc.jpa.dto.TeamDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//db와 연동될 table 생성
@Entity //객체와 테이블 mapping 어노테이션
@Table(name = "team") // 위와 동일한 역할
@Getter
@NoArgsConstructor //빈 생성자로 만들 수 있게 함.
@AllArgsConstructor //모든 필드 값을 파라미터로 받는 생성자를 만듦.
@Builder //객체를 정의하고 객체를 생성할 때 보통 생성자를 통해 생성하는 것을 의미함.
public class Team extends BaseTimeEntity { //모든 Entity의 상위 클래스에서 createdDate, updateDate를 자동으로 관리해주는 역할을 함.
    @Id //기본키 mapping
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 생성 전략 중 기본키 생성을 db에 위임. AUTO_INCREMENT 와 같은 역할.
    @Column(name = "id", nullable = false)
    private Long id;

    //객체 필드를 테이블에 mapping . name과 nullable이 주로 사용됨.
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    //cascadetype.remove는 사라져도 null로 남는데 orphanRemoval는 다 사라짐.
    //@onetomany는 연관관계를 나타내는 어노테이션. 1:N 관계
    //mappedBy: 양방향 사용 시 관계의 주체가 되는 쪽 정의. fetch: 관계 entity의 데이터 읽기 전략을 실제로 요청하는 순간 정보를 가져오는 것으로 설정.
    //cascade: 현 Entity의 변경에 대해 관계를 맺은 Entity도 변경 전략결정. orphonRemoval: 관계 Entity에서 변경이 일어난 경우 DB 변경을 같이 할지 결정.
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Member> members = new ArrayList<>();


    public TeamDTO toDTO() { //이처럼 빌더패턴 사용 시 가독성을 높일 수 있음.
        return TeamDTO.builder()
                .id(id)
                .name(name)
                .createDate(createDate)
                .lastModifiedDate(lastModifiedDate)
                .build();
    }

    public void update(TeamDTO dto) {
        this.name = dto.getName();
    }
}
