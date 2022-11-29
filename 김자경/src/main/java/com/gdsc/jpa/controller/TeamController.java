package com.gdsc.jpa.controller;

import com.gdsc.jpa.dto.TeamDTO;
import com.gdsc.jpa.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController // Json 형태로 객체 데이터를 반환
@RequiredArgsConstructor // 생성자 주입
@RequestMapping("/api") // 'localhost:8080/api' 형태로 매핑 됨
public class TeamController {
    private final TeamService teamService;

    // TeamDto parameter를 받아 teamService.save()에 삽입
    @PostMapping("/teams")
    public ResponseEntity<TeamDTO> save(@RequestBody TeamDTO request) {
        TeamDTO response = teamService.save(request);
        return ResponseEntity.created(URI.create("/api/teams/" + response.getId()))
                .body(response);
    }

    // 모든 team 조회
    @GetMapping("/teams")
    public ResponseEntity<List<TeamDTO>> findAll() {
        List<TeamDTO> responses = teamService.findAll();
        // 만약 비어있으면 No Content
        // 201(No Content) : body에 응답 내용이 없을 경우 이용
        if(responses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        // 비어있지 않으면 요청 성공
        // 200(OK) : 요청 성공의 기본 상태 코드
        return ResponseEntity.ok(responses);
    }

    // id를 이용하여 team 조회
    @GetMapping("/teams/{id}")
    public ResponseEntity<TeamDTO> findById(@PathVariable("id") Long id) {
        TeamDTO responses = teamService.findById(id);
        return ResponseEntity.ok(responses);
    }

    // id를 이용하여 team 조회한 후 이름(name) 변경
    @PatchMapping("/teams/{id}")
    public ResponseEntity<TeamDTO> updateById(@PathVariable("id") Long id, @RequestBody TeamDTO request) {
        TeamDTO response = teamService.updateById(id, request);
        return ResponseEntity.ok(response);
    }

    // id를 이용하여 team 조회한 후 삭제
    @DeleteMapping("items/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        teamService.deleteById(id);
        return ResponseEntity.ok(null);
    }
}