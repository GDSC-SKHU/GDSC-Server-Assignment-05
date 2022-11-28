package com.gdsc.jpa.repository;

import com.gdsc.jpa.entity.Member;
import com.gdsc.jpa.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


// Repository : JPA에서의 DB Layer 접근자를 의미
/**인터페이스를 생성 후 JpaRepository<Entity 클래스, PK 타입>을 상속하면
기본적인 CRUD 메소드가 자동으로 생성된다.
 */
//@Repository를 추가할 필요가 없다.
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findAllByTeam(Team team);


}