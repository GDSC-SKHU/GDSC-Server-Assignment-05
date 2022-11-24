package com.gdsc.gdscjpaexample.repository;

import com.gdsc.gdscjpaexample.entity.Member;
import com.gdsc.gdscjpaexample.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
   스프링부트에서는 Entity의 기본적인 CRUD가 가능하도록 JpaRepository 인터페이스를 제공
   Spring Data JPA에서 제공하는 JpaRepository 인터페이스를 상속하기만 해도 됨
   인터페이스에 따로 @Repository 어노테이션을 추가할 필요가 없음

   extends JpaRepository<Entity class, Id>
*/

public interface MemberRepository extends JpaRepository<Member, Long> {

    // findAllByTeam, 구현해야 함
    List<Member> findAllByTeam(Team team);
}
