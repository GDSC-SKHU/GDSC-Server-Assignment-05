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
    @Transactional
    public TeamDTO save(TeamDTO dto){
        Team team = Team.builder().name(dto.getName()).build();
        teamRepository.save(team);

        return team.toDTO();
    }
    // 모든 팀 검색
    @Transactional(readOnly = true)
    public List<TeamDTO> findAll(){
        List<Team> teams = teamRepository.findAll();
        return teams.stream()
                .map(Team::toDTO)
                .collect(Collectors.toList());
    }
    // 팀 검색
    @Transactional(readOnly = true)
    public TeamDTO findById(Long id){
        Team team = findEntityByID(id);

        return team.toDTO();
    }
    //팀 객체 update
    @Transactional
    public TeamDTO updateById(Long id, TeamDTO dto){
        Team team = findEntityByID(id);
        team.update(dto);
        teamRepository.saveAndFlush(team);

        return team.toDTO();
    }

    //팀 객체 delete
    @Transactional
    public void deleteById(Long id){
        Team team = findEntityByID(id);
        teamRepository.delete(team);
    }

    Team findEntityByID(Long id){
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 팀이 존재하지 않습니다."));

    }
}
