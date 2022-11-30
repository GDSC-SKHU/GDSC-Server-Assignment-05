package com.gdsc.jpa.repository;

import com.gdsc.jpa.entity.Member;
import com.gdsc.jpa.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
// Spring Data JPA가 프록시 객체를 만들어서 필요한 곳에 인터페이스 구현체를 집어 넣어줌
// @Repository 생략 가능
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByTeam(Team team);
}