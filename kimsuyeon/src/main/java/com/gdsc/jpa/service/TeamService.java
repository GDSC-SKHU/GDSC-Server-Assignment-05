package com.gdsc.jpa.service;


import com.gdsc.jpa.dto.TeamDto;
import com.gdsc.jpa.entity.Team;
import com.gdsc.jpa.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service //서비스 등록
@RequiredArgsConstructor //final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class TeamService {

    // 의존성 주입
    private final TeamRepository teamRepository;


    //CRUD
    //팀 생성
    @Transactional //데이터베이스의 상태를 변경하는 작업 또는 한번에 수행되어야 하는 연산들을 의미
    public TeamDto save(TeamDto dto) {//dto 기반으로 create team

        // 빌더패턴을 통해 어떤 필드에 어떤 값을 넣어주는지 명확히 눈으로 확인할 수 있다
        Team team = Team.builder()
                .name(dto.getName())
                .build();
        teamRepository.save(team);

        return team.toDTO();//toDTO()로 반환
    }

    @Transactional(readOnly = true)
    public List<TeamDto> findAll() {//모든 TeamDto를 List로 조회
        List<Team> teams = teamRepository.findAll();//모든 팀을 리스트로 가져온다
        // 반환
        return teams.stream() //1. 스트림 생성 : List와 같은 컬렉션 인스턴스를 스트림으로 생성
                .map(Team::toDTO)// 2. 가공 : 람다식의 '메서드 참조'를 통해 좀 더 코드를 간결하게!

                /*메서드 참조는 '클래스명::메서드명', '참조변수::메서드'와 같이 작성해서
                람다식이 하나의 메서드만을 호출하는 경우 람다식을 간단하게 할 수 있는 방법
                 */
                .collect(Collectors.toList());//3. 결과 반환 : 가공된 Stream을 List라는 최종 결과로 만들어 반환
    }

    @Transactional(readOnly = true)
        public TeamDto findById(Long id){//id로 team찾기
            Team team = findEntityById(id);
            return team.toDTO();
    }

    @Transactional
    public TeamDto updateById(Long id,TeamDto dto){
        Team team=teamRepository.findById(id) // 해당 ID의 팀이 존재하지 않을 경우
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 ID의 팀이 존재하지 않습니다"));
                //오류발생

        team.update(dto);
        teamRepository.saveAndFlush(team); //saveAndFlush() 메소드는 Spring Data JPA 에서 정의한
                                          // JpaRepository 인터페이스의 메소드
                                //saveAndFlush() 메소드는 즉시 DB 에 변경사항을 적용하는 방식
                        return dto;
                        //id로 팀을 찾고 dto로 바꿔서 update 후 리턴
    }

    @Transactional
    public void deleteById(Long id){ //id를 이용하여 team id와 일치하는 팀 객체 삭제
        Team team=findEntityById(id);
        //deleteById를 사용하면 내부적인 findById 조회 시
        //값이 없을 경우 EmptyResultDataAccessException 발생
        teamRepository.delete(team);
    }
    @Transactional(readOnly = true)
    Team findEntityById(Long id){ //id로 팀찾기
        return teamRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 ID의 팀이 존재하지 않습니다"));
                //없으면 예외 발생
                //HTTP 상태 코드를 지정해준다.
                //예) @ResponseStatus(value = HttpStatus.NOT_FOUND)
    }
}

