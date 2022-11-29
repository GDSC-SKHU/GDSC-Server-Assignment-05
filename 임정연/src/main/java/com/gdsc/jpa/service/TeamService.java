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

@Service //비지니스 로직이 들어가는 Service로 사용되는 클래스임을 명시하는 어노테이션
@RequiredArgsConstructor //final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 만들어준다
public class TeamService {
    private final TeamRepository teamRepository;

    //CRUD 구성
    @Transactional
    /*
    Transaction이란 어떤 작업에서 오류가 발생했을 때는 이전에 있던 모든 작업들이 성공적이었더라도 없었던 일 처럼 완전히 되돌리는 것
    어떤 작업을 처리하던 중 오류가 발생했을 때 모든 작업들을 원상태로 되돌릴 수 있다
    일련의 작업들을 묶어서 하나의 단위로 처리하고 싶을떄 @Transational을 활용
     */
    public TeamDTO save(TeamDTO dto){ //이름(팀명)으로 팀 생성
        Team team = Team.builder()
                .name(dto.getName())
                .build();

        teamRepository.save(team);
        //save()메소드는 엔티티를 DB에 저장하는 기능을 수행, 바로 DB에 저장되지 않고 영속성 컨텍스트에 저장되었다가 flush()또는 commit() 수행시 db에 저장됨
        return team.toDTO();
    }
    @Transactional(readOnly = true)
    //모든 TeamDTO를 리스트로 조회
    public List<TeamDTO> findAll(){
        List<Team> teams = teamRepository.findAll(); //모든 팀을 리시트로 가져오기

        return teams.stream() //여기서 .stream()은 아래 조건에 부합하는 데이터만을 선택하는 것
                .map(Team::toDTO) //모든 팀을 dto로 바꾼다
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    //해당 id로 팀 찾기
    public TeamDTO findById(Long id){
        Team team = findEntityById(id);
         //      .orElseThrow(() -> new ResponseStatusException (HttpStatus.NOT_FOUND, "해당 ID의 팀이 존재하지 않습니다"));
                return team.toDTO();
    }

    @Transactional
    //해당 id의 팀 정보 수정
    public TeamDTO updateById(Long id, TeamDTO dto){
       Team team = findEntityById(id);
           //     .orElseThrow(() ->  new ResponseStatusException (HttpStatus.NOT_FOUND, "해당 ID의 팀이 존재하지 않습니다"));
        team.update(dto);
        teamRepository.saveAndFlush(team); //saveAndFlush()는 즉시 db에 변경사항을 적용하는 방식

        return team.toDTO();
    }

    @Transactional
    //해당 id의 팀 삭제
    public void deleteById(Long id){
        Team team = findEntityById(id);
        teamRepository.deleteById(id);
    }

    Team findEntityById(Long id){ //반복되는 부분을 따로 뺌
        return teamRepository.findById(id)
                .orElseThrow(() ->  new ResponseStatusException (HttpStatus.NOT_FOUND, "해당 ID의 팀이 존재하지 않습니다"));
     /*
            ResponseStatusException은 @ResponseStatus의 대체제이다, 비슷한 유형의 예외를 별도로 처리할 수 있고
            응답마다 다른 상태 코드를 세팅할 수 있다
            불필요한 Exception 클래스 생성을 피할 수 있다
            Exception 처리를 추가적인 어노테이션 없이 코드 단에서 자연스럽게 처리
             */
    }
}

