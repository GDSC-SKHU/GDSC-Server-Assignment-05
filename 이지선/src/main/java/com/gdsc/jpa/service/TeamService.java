package com.gdsc.jpa.service;

import com.gdsc.jpa.dto.TeamDTO;
import com.gdsc.jpa.entity.Team;
import com.gdsc.jpa.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

// Service: 사용자의 요구사항을 처리하고, DB 데이터가 필요할 때는 repository에게 요청
@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;            // 생성자를 사용하여 의존성을 주입

    // springframework의 Transactional 어노테이션 사용
    // 트랜잭션의 성질 - 원자성, 일관성, 격리성, 지속성
    // @Transactional을 붙이면 해당 메소드의 작업 중에 하나라도 실패할 경우 전체 작업을 취소한다.
    @Transactional
    public TeamDTO save (TeamDTO dto) {
        Team team = Team.builder()      // 빌더를 사용해 전달받은 값으로 team 객체 생성
                .name(dto.getName())
                .build();

        teamRepository.save(team);      // DB 저장

        return team.toDTO();            // TeamDTO 객체로 반환
    }

    // 읽기 전용, 성능 최적화 or 쓰기작업 방지
    @Transactional(readOnly=true)
    public List<TeamDTO> findAll() {
        List<Team> teams = teamRepository.findAll();

        // team 객체들을 teamDTO로 변경하고, 스트림 요소들을 List로 변환
        return teams.stream()
                .map(Team::toDTO)
                .collect(Collectors.toList());
    }

    // id값을 기준으로 team 조회
    @Transactional(readOnly=true)
    public TeamDTO findById(Long id) {
        Team team = findEntityById(id);

        return team.toDTO();
    }

    // id값을 기준으로 team 객체 갱신
    @Transactional
    public TeamDTO updateById(Long id, TeamDTO dto) {
        Team team = findEntityById(id);
        team.update(dto);           

        // update된 객체 DB에 저장
        // save 메소드는 flush() 또는 commit()가 수행될 때 DB에 저장되고, saveAndFlush 메소드는 즉시 저장된다.
        teamRepository.saveAndFlush(team);
        return team.toDTO();
    }

    Team findEntityById(Long id) {
        // orElseThrow 메소드는 null일 경우 예외 처리 -> 404 Not Found 상태코드와 메시지 전달
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 ID의 팀은 존재하지 않습니다."));
    }
    
    @Transactional
    public void deleteById(Long id) {
        Team team = findEntityById(id);
        
        // delete 메소드를 사용해야 값이 없을 경우의 예외처리 가능
        teamRepository.delete(team);
    }

}
