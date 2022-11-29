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


@Service //클래스를 루트컨테이너에 bean객체로 생성해주는 어노테이션.
@RequiredArgsConstructor //final이나 @NonNull인 필드 값만 파라미터로 받는 생성자 만듦
public class TeamService {

    private final TeamRepository teamRepository;

    //CRUD

    //team 생성
    @Transactional
    public TeamDTO save(TeamDTO dto){
        Team team = Team.builder()
                .name(dto.getName())
                .build(); //Team team = new ~~ 를 가독성있게해줌.

        teamRepository.save(team);

        return team.toDTO();
    }
    //전체 team을 list에 담아서 return
    @Transactional(readOnly = true) //트랜젝션을 읽기전용으로 설정
    public List<TeamDTO> findAll() {
        List<Team> teams = teamRepository.findAll();

        return teams.stream()
                .map(Team::toDTO)
                .collect(Collectors.toList());
    }

    //매개변수의 id와 일치하는 id를 가진 team이 있는지 확인. 없으면 에러메세지 출력.
    @Transactional(readOnly = true)
    public TeamDTO findById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 ID의 팀이 존재하지 않습니다."));

        return team.toDTO();
    }


    //db에 있는 팀 중 id가 같은 팀의 정보를 수정하여 return
    @Transactional
    public TeamDTO updateById(Long id, TeamDTO dto) {
        Team team = teamRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 ID의 팀이 존재하지 않습니다."));

        team.update(dto);
        teamRepository.saveAndFlush(team);

        return team.toDTO();
    }


    //일치하는 id를 가진 team 삭제
    @Transactional
    public void deleteById(Long id) {
        Team team = findEntityById(id);

        teamRepository.delete(team);

//        teamRepository.deleteById(id);
    }

    //db id로 Team 찾고 없으면 error message
    Team findEntityById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 ID의 팀이 존재하지 않습니다."));
    }
}
