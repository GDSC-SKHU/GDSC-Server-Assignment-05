package com.gdsc.jpa.controller;

import com.gdsc.jpa.dto.TeamDTO;
import com.gdsc.jpa.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

// Controller: 사용자의 요청을 받아 요청에 대한 처리는 service에게 넘겨주고, service가 처리한 내용을 받아 view에게 넘겨준다.
@RestController             // @Controller는 주로 View를 반환하고, @RestController는 JSON/XML형태로 객체 데이터를 반환
@RequiredArgsConstructor    // final이 붙거나 @NotNull이 붙은 필드의 생성자를 자동 생성
@RequestMapping("/api")  // 공통 url 설정
public class TeamController {
    private final TeamService teamService;


    // Post 방식 /api/teams
    // 서버->클라이언트 응답(response) | 클라이언트->서버 요청(request), JSON 형식의 데이터를 RequestBody에 담아서 보냄
    // @RequestBody 어노테이션이 붙은 파라미터에는 http 요청의 본문(body)이 그대로 전달
    @PostMapping("/teams")
    public ResponseEntity<TeamDTO> save(@RequestBody TeamDTO request) {
        TeamDTO response = teamService.save(request);       // 받아온 요청을 DB에 저장

        // ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스로 <Body, Headers, Status>를 포함한다.
        return ResponseEntity
                .created(URI.create("/api/"+response.getId()))      // 201 Created 상태코드는 새로 생성된 자원의 주소를 함께 반환을 해야함.
                .body(response);
    }


    // team 전체 조회
    @GetMapping("/teams")
    public ResponseEntity<List<TeamDTO>> findAll() {
        List<TeamDTO> responses = teamService.findAll();

        // 값이 존재하지 않을 경우
        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()            // 204 No Content - 요청이 성공했으나 클라이언트가 현재 페이지에서 벗어나지 않음.
                    .build();
        }

        return ResponseEntity.ok(responses);        // 200 OK - 요청 성공
    }


    // id값을 기준으로 team 조회
    @GetMapping("/teams/{id}")
    // @PathVariable는 URL 경로에 변수를 넣어줄 때 사용
    public ResponseEntity<TeamDTO> findById(@PathVariable("id") Long id) {
        TeamDTO response = teamService.findById(id);

        return ResponseEntity
                .ok(response);
    }

    // patch - 일부 갱신 / put - 전체 갱신
    @PatchMapping("/teams/{id}")
    public ResponseEntity<TeamDTO> updateById(@PathVariable("id") Long id, @RequestBody TeamDTO request) {
        TeamDTO response = teamService.updateById(id, request);

        return ResponseEntity
                .ok(response);
    }

    // 팀 삭제, 반환값 없음
    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        teamService.deleteById(id);

        return ResponseEntity
                .ok(null);
    }
}
