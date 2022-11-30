package com.gdsc.jpa.repository;

import com.gdsc.jpa.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

// Repository: DB에 접근하는 메소드들을 사용하기 위한 인터페이스, DB의 CRUD 작업 처리
// TeamRepository 인터페이스에 JpaRepository를 상속. <Entity 클래스 명, PK의 자료형> -> JPA가 기본적으로 제공하는 메소드 사용 가능
// JPA buddy 플러그인으로 repository 자동 생성 가능
public interface TeamRepository extends JpaRepository<Team, Long> {
}
