package com.gdsc.jpa.repository;

import com.gdsc.jpa.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
// jpa 사용
public interface TeamRepository extends JpaRepository<Team,Long> {
}
