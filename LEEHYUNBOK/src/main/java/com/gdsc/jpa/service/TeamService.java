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

    // 실행 과정에서 오류 발생 시 이 함수 이전으로 롤백
    @Transactional
    public TeamDTO save(TeamDTO dto){
        Team team = Team.builder().name(dto.getName()).build();

        teamRepository.save(team);

        return team.toDTO();
    }

    @Transactional(readOnly = true)
    public List<TeamDTO> findAll(){
        List<Team> teams = teamRepository.findAll();

        return teams.stream()
                .map(Team::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TeamDTO findById(Long id){
        Team team = findEntityById(id);

        return team.toDTO();
    }

    @Transactional
    public TeamDTO updateById(Long id, TeamDTO dto){
        Team team = findEntityById(id);


        team.update(dto);
        teamRepository.saveAndFlush(team);

        return team.toDTO();
    }

    @Transactional
    public void deleteById(Long id){
        Team team = findEntityById(id);

        teamRepository.delete(team);
    }

    Team findEntityById(Long id){
        return teamRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 ID의 팀이 존재하지 않습니다."));
    }
}
