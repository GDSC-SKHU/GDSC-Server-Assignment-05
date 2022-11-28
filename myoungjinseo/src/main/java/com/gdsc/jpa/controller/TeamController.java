package com.gdsc.jpa.controller;

import com.gdsc.jpa.dto.TeamDTO;
import com.gdsc.jpa.dto.TeamDTO;
import com.gdsc.jpa.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController // https://mangkyu.tistory.com/49
@RequiredArgsConstructor
@RequestMapping("/api")
public class TeamController {

    private final TeamService teamService;

    // teamDto 파라미터를 받아와서 teamService.save() 에 넣어준다
    // /api/teams/{id} 를 uri 로 만들어준다.
    // 요청응답 코드가 전달이 된다.
    @PostMapping("/teams")
    public ResponseEntity<TeamDTO> save(@RequestBody TeamDTO request) {
        TeamDTO response = teamService.save(request);

        return ResponseEntity
                .created(URI.create("/api/teams/" + response.getId()))
                .body(response);
    }

    // findAll 를 통해 team 객체를 받아옴
    // List 가 비어 있을 경우 noContent => 204 에러 발생시킴
    // 아니면 200 요청 성공 코드 반환
    @GetMapping("/teams")
    public ResponseEntity<List<TeamDTO>> findAll() {
        List<TeamDTO> responses = teamService.findAll();

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity.ok(responses);
    }
    // id 값을 받아오고 id에 맞는 team 객체를 받아옴
    @GetMapping("/teams/{id}")
    public ResponseEntity<TeamDTO> findById(@PathVariable("id") Long id) {
        TeamDTO response = teamService.findById(id);

        return ResponseEntity.ok(response);
    }

    // id, TeamDto 를 받아오고 id 에 맞는 team 객체 수정
    @PatchMapping("/teams/{id}")
    public ResponseEntity<TeamDTO> updateById(@PathVariable("id") Long id, @RequestBody TeamDTO request) {
        TeamDTO response = teamService.updateById(id, request);

        return ResponseEntity.ok(response);
    }

    // id 를 받아오고 id 에 맞는 team 객체 삭제
    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        teamService.deleteById(id);

        return ResponseEntity.ok(null);
    }
}