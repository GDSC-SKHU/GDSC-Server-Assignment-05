package com.gdsc.gdscjpaexample.service;

import com.gdsc.gdscjpaexample.dto.TeamDTO;
import com.gdsc.gdscjpaexample.entity.Team;
import com.gdsc.gdscjpaexample.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/*
   public TeamDTO save(TeamDTO dto)
   -> 매개변수로 받은 dto 기반으로 create team
   public List<TeamDTO> findAll()
   -> DB에 존재하는 모든 팀 정보를 리스트에 담아 리턴
   public TeamDTO findById(Long id)
   -> 매개변수로 받은 id와 일치하는 id를 가진 team 리턴
   public TeamDTO updateById(Long id, TeamDTO dto)=
   -> 매개변수로 받은 id로 팀을 찾고 dto 기반으로 update 후 리턴
   public void deleteById(Long id)
   -> 매개변수로 받은 id와 일치하는 id를 가진 team 제거
   Team findEntityById(Long id)
   -> 접근제어자 public 안씀, 실질적인 DB에 접근하여 멤버를 가져오는 메소드이기 때문에,
      다른 쪽에서는 접근할 수 없도록 함
*/

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    // Create, 팀 생성
    @Transactional
    public TeamDTO save(TeamDTO dto) {
        Team team = Team.builder()
                .name(dto.getName())
                .build();

        teamRepository.save(team);

        return team.toDTO();
    }

    // DB에 존재하는 팀 정보를 모두 리스트에 담아 리턴
    @Transactional(readOnly = true)
    public List<TeamDTO> findAll() {
        // DB에 존재하는 모든 팀을 리스트로 가져옴
        List<Team> teams = teamRepository.findAll();

        // List<TeamDTO>로 만들어 리턴
        return teams.stream()
                .map(Team::toDTO)
                .collect(Collectors.toList());
    }

    // DB에 존재하는 팀 중에서 매개변수로 받은 id와 동일한 id를 가진 팀 정보를 리턴
    @Transactional(readOnly = true)
    public TeamDTO findById(Long id) {
        Team team = findEntityById(id);

        return team.toDTO();
    }

    // DB에 존재하는 팀 중에서 매개변수로 받은 id와 동일한 id를 가진 팀 정보를 수정하여 리턴
    @Transactional
    public TeamDTO updateById(Long id, TeamDTO dto) {
        Team team = findEntityById(id);

        team.update(dto);
        teamRepository.saveAndFlush(team);

        return team.toDTO();
    }

    // DB에 존재하는 팀 중에서 매개변수로 받은 id와 동일한 id를 가진 팀을 삭제
    @Transactional
    public void deleteById(Long id) {
        Team team = findEntityById(id);

        teamRepository.delete(team);
    }

    // 실질적인 DB에 id로 팀 찾기, 없으면 에러 메시지
    Team findEntityById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, " 해당 ID의 팀이 존재하지 않습니다."));

    }
}
