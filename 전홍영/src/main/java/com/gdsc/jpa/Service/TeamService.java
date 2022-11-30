package com.gdsc.jpa.Service;

import com.gdsc.jpa.dto.TeamDto;
import com.gdsc.jpa.entity.Team;
import com.gdsc.jpa.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    //CRUD
    @Transactional
    public TeamDto save(TeamDto dto) {
        //team 생성
        Team team = Team.builder()
                .name(dto.getName())
                .build();
        //생성된 team 저장
        teamRepository.save(team);
        return team.toDto();
    }

    @Transactional(readOnly = true)//읽기 전용
    public List<TeamDto> findALl() {
        //모든 team 조회
        List<Team> teams = teamRepository.findAll();
        //stream을 이용해 모든 team을 teamDto로 변환하여 반환
        return teams.stream()
                .map(Team::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TeamDto findById(Long id) {
        //id로 team 조회
        Team team = findEntityById(id);
        //조회한 team을 Dto로 변환
        return team.toDto();
    }

    @Transactional
    public TeamDto updateById(Long id, TeamDto dto) {
        //id로 team 조회
        Team team = findEntityById(id);
        //조회한 team을 파라미터로 받은 TeamDto로 update
        team.update(dto);
        //update한 team을 저장
        return teamRepository.saveAndFlush(team).toDto();
    }

    @Transactional
    public void deleteById(Long id) {
        //id에 맞는 team 조회
        Team team = findEntityById(id);
        //해당 team 삭제
        teamRepository.delete(team);

    }

    private Team findEntityById(Long id) {
        //파라미터로 받은 id에 맞는 team을 조회한다. 만약 team이 존재하지 않으면 오류를 던진다.
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 팀이 존재하지 않습니다."));
        return team;
    }
}
