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

    // Transactional 어노테이션 : 실행 과정에서 오류 발생 시 이 함수 이전으로 롤백
    // => 해당 어노테이션이 적용되는 메소드를 하나의 트랜잭션으로 묶어주는 역할
    // https://goddaehee.tistory.com/167
    @Transactional
    public TeamDTO save(TeamDTO dto){
        // builder() : 생성자에서 인자가 많을때 고려해볼수있는것이 빌더패턴
        // -> 쉽게 생성하도록 도와줌
        Team team = Team.builder().name(dto.getName()).build();

        teamRepository.save(team);

        return team.toDTO();
    }

    // @Transactional(readOnly = true) : 트렌직션 읽기 모드
    // 영속성 컨텍슽트는 변경 감지를 위해 스냅샷 인스턴스를 보관하므로 더 많은 메모리 사용하는 단점 존재
    // 조회만 하는 경우 읽기 전용으로 엔티티를 조회하면 메모리 사용량 최적화 가능
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

    // teamRepository를 통해 id를 찾지만 찾지 못한다면 NOT_FOUND 오류 throw
    Team findEntityById(Long id){
        return teamRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 ID의 팀이 존재하지 않습니다."));
    }
}
