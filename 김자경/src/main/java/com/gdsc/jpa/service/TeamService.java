package com.gdsc.jpa.service;

import com.gdsc.jpa.dto.TeamDTO;
import com.gdsc.jpa.entity.Team;
import com.gdsc.jpa.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service // 핵심 비즈니스 로직을 담은 서비스 클래스를 bean으로 등록
@RequiredArgsConstructor // 클래스에 선언된 final 변수들, 필드들을 매개변수로 하는 생성자를 자동으로 생성
public class TeamService {
    // 생성자를 사용하여 의존성 주입
    private final TeamRepository teamRepository;

    // CRUD -> Create(생성)
    // 팀 등록
    @Transactional
    public TeamDTO save(TeamDTO dto) {
        // dto를 이용하여 team 이름(name) 등록
        Team team = Team.builder()
                .name(dto.getName())
                .build();
        //teamRepository에 team 정보 저장
        teamRepository.save(team);
        return team.toDTO();
    }

    // CRUD -> Read(읽기)
    // 모든 팀 조회
    @Transactional(readOnly = true)
    public List<TeamDTO> findAll() {
        List<Team> teams = teamRepository.findAll();
        return teams.stream()
                .map(Team::toDTO)
                .collect(Collectors.toList());
    }

    // id로 팀 조회
    @Transactional(readOnly = true)
    public TeamDTO findById(Long id) {
        Team team = findEntityById(id);
        return team.toDTO();
    }

    // CRUD -> Update(갱신)
    @Transactional
    public TeamDTO updateById(Long id, TeamDTO dto) {
        Team team = findEntityById(id);
        team.update(dto);
        //// 실행중(트랜잭션)에 즉시 data를 flush -> 데이터를 댐처럼 쏟아낸다고 해야하나 암튼 그럼
        teamRepository.saveAndFlush(team);
        return team.toDTO();
    }

    // CRUD -> Delete(삭제)
    @Transactional
    public void deleteById(Long id) {
        Team team = findEntityById(id);
        teamRepository.delete(team);
        // 아래 방식도 가능하지만 위 방식으로 에러 핸들링을 지향하자
        // teamRepository.deleteById(id);
    }

    // CRUD -> Read(읽기)
    @Transactional(readOnly = true)
    Team findEntityById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음"));
    }
}
