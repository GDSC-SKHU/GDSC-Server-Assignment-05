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

    // CRUD 구성
    @Transactional
    public TeamDTO save(TeamDTO dto){
        Team team = Team.builder() // builder를 사용하여 값을 넣어줌
                .name(dto.getName())
                .build();
        teamRepository.save(team); // 위의 값을 팀 레포지토리에 저장

        return team.toDTO(); // return teamRepository.save(team).toDTO();
    }

    @Transactional(readOnly = true) // 예상치못한 엔티티의 등록, 변경, 삭제 예방하고 성능 최적화시켜줌
    public List<TeamDTO> findAll(){
        List<Team> teams = teamRepository.findAll();

        return teams.stream()
                .map(Team::toDTO)
                .collect(Collectors.toList()); // stream을 사용하여 list로 반환
    }

    @Transactional(readOnly = true) // 예상치못한 엔티티의 등록, 변경, 삭제 예방하고 성능 최적화시켜줌
    public TeamDTO findById(Long id){
        Team team = findEntityById(id); // id를 찾아줌

        return team.toDTO(); // 찾은 id 반환
    }

    @Transactional
    public TeamDTO updateById(Long id, TeamDTO dto){
        Team team = findEntityById(id);
        team.update(dto);
        teamRepository.saveAndFlush(team); // 실행중에 데이터 flush

        return team.toDTO();
    }

    @Transactional
    public void deleteById(Long id){
        Team team = findEntityById(id); // 해당하는 팀만 지워지도록 따로 설정해둠
        teamRepository.deleteById(id);
    }

    Team findEntityById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 팀이 존재하지 않습니다."));
                // optional의 인자가 null일 경우 예외 발생처리. 찾지 못할 경우 예외처리해둠
    }


}
