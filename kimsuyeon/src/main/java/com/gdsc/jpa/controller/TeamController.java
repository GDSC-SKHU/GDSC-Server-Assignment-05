package com.gdsc.jpa.controller;


import com.gdsc.jpa.dto.TeamDto;
import com.gdsc.jpa.entity.Team;
import com.gdsc.jpa.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor//초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성
@RequestMapping("/api") //URL 을 컨트롤러의 메서드와 매핑할 때 사용하는 어노테이션
public class TeamController {


    //의존성 주입
    private final TeamService teamService;

    /*ResponseEntity란, httpentity를 상속받는,
     결과 데이터와 HTTP 상태 코드를 직접 제어할 수 있는 클래스이다.
     Spring에서는 body와 헤더 정보, 상태 코드 등을 담을 수 있는
     ResponseEntity가 제공

     사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스
     */

    /**
     * @RequestBody 애너테이션의 역할은
     * 클라이언트가 보내는 HTTP 요청 본문(JSON 및 XML 등)을 Java 오브젝트로 변환
     */

    //teamDto 를 받아와서 teamService.save()에 저장
    @PostMapping("/teams")
    public ResponseEntity<TeamDto> save(@RequestBody TeamDto request) {
        TeamDto response = teamService.save(request);

        return ResponseEntity.created(URI.create("/api/" + response.getId()))
                .body(response);//create : 요청이 성공적이였으며 그 결과로 새로운 리소스가 생성되었다는 의미(/api/teams/{id})

    }


    @GetMapping("/teams")
    public ResponseEntity<List<TeamDto>> findAll() { //모든 팀을 조회
        //ResponseEntity는 List로  TeamDto 반환
        List<TeamDto> response = teamService.findAll();

        if (response.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity // 결과 응답(팀 출력)
                .ok(response);
    }

    @GetMapping("/teams/{id}")  //해당id를 통해 팀을 찾음
    public ResponseEntity<TeamDto> findById(@PathVariable Long id) {
        TeamDto response = teamService.findById(id);//DB 에 요청해서 엔티티를 가져옴

        return ResponseEntity
                .ok(response);
    }

    @PatchMapping("/teams/{id}")//해당 id를 통해 팀을 찾고 업데이트
    public ResponseEntity<TeamDto> updateById(@PathVariable Long id, @RequestBody TeamDto request) {
        TeamDto response = teamService.updateById(id, request);

        return ResponseEntity
                .ok(response);

    }

    @DeleteMapping("/teams/{id}") //해당id를 통해 팀 삭제
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        teamService.deleteById(id);

        return ResponseEntity.ok(null);
    }
}
/*
201 Created (새로운 리소스가 생성됨)
요청 성공 후에 새로운 리소스가 생성된 경우이다.
보통 생성된 리소스는 응답의 Location 헤더 필드로 식별한다.


202 Accepted (요청 접수가 완료됐지만, 예약된 시간에 실행될 것임)
즉시 어떠한 행동을 하지 않고 스케쥴링된 시간에 행위를 하겠다는 의미이다.
이를테면 1시간 뒤에 배치 처리를 하겠다는 명령이 될 수 있다.


204 No Content (서버가 요청을 성공적으로 수행했지만, 응답 페이로드 본문에 보낼 데이터가 없음)
요청에 대한 응답은 성공적이지만, 딱히 성공으로 인해 생성된 데이터 등을 보낼 이유가 없을 때이다.
이를테면 사용자 정보 수정 페이지에서 사용자 정보가 수정되었을 때, 화면에 이미 수정된 사용자 정보가 나와있다면, 굳이 반환해줄 이유가 없다.

ResponseEntity 객체에서는
created(201) 뿐만 아니라, accepted(202), noContent(204), badRequest(400),
internalServerError(500), notFound(404) 등 자주 쓰이는 상태코드를
가독성 좋은 메소드로 제공한다.
 */