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

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    // CRUD
    // 팀 저장
    @Transactional
    public TeamDTO save(TeamDTO dto){
        Team team = Team.builder()
                .name(dto.getName())
                .build();
        teamRepository.save(team);
        return team.toDto();

    }

    // 모든 팀 찾기
    @Transactional(readOnly = true)
    public List<TeamDTO> findAll(){
        List<Team> teams = teamRepository.findAll();

        return teams.stream()
                .map(Team::toDto)
                .collect(Collectors.toList());
    }

    // id로 팀 찾기
    @Transactional(readOnly = true)
    public TeamDTO findById(Long id){
        Team team = findEntityById(id);
        return team.toDto();
    }

    // id를 이용하여 팀 객체 변경
    @Transactional
    public TeamDTO updateById(Long id, TeamDTO dto){
        Team team = findEntityById(id);
        team.update(dto);
        teamRepository.saveAndFlush(team);
        return team.toDto();
    }


    //  id를 이용하여 팀 객체 삭제
    @Transactional
    public void deleteById(Long id){
        Team team = findEntityById(id);

        teamRepository.delete(team);
    }


    Team findEntityById(Long id){
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 ID의 팀이 존재하지 않습니다."));
    }
}
