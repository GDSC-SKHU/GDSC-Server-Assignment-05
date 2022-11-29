package com.gdsc.jpa.repository;

import com.gdsc.jpa.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;


// extends JpaRepository<Team,Long> : 간단한 CRUD 기능을 공통으로 처리하는 인터페이스
// 간단하지만 단순 반복 작업들을 Spring Data JPA 구현체인 Hibernate가
// 애플리케이션 실행 시점에 동적으로 자주 사용되는 쿼리 집합을 만들어 우리의 UserRepository 인터페이스 구현 가능
public interface TeamRepository extends JpaRepository<Team,Long> {
}
