package com.gdsc.jpa.controller;

import com.gdsc.jpa.dto.TeamDTO;
import com.gdsc.jpa.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TeamController {
    private final TeamService teamService;

    // teamDto 를 받아오고
    // MemberDto 파라미터를 받아와서 builder 를 통해  memberService.save() 를 만듬
    // /api/teams/{id} uri 를 결과값으로 보낸다.
    @PostMapping("/teams")
    public ResponseEntity<TeamDTO> save(@RequestBody TeamDTO request){
        TeamDTO response = teamService.save(request);
        return ResponseEntity
                .created(URI.create("/api/" + response.getId()))
                .body(response);
    }

    // id 값을 받고 id에 맞는 findAll 를 통해 team 객체를 받아옴
    // 200 요청 성공 코드 반환
    @GetMapping("/teams")
    public ResponseEntity<List<TeamDTO>> findAll(){
        List<TeamDTO> responses = teamService.findAll();

        return ResponseEntity
                .ok(responses);
    }

    // id 값을 받고 id에 맞는 findById 를 통해 team 객체를 받아옴
    // 200 요청 성공 코드 반환
    @GetMapping("/teams/{id}")
    public ResponseEntity<TeamDTO> findById(@PathVariable Long id){
        TeamDTO response = teamService.findById(id);

        return ResponseEntity
                .ok(response);
    }

    // id, memberDto 를 받아오고 id 에 맞는 member 객체 수정
    @PatchMapping("/teams/{id}")
    public ResponseEntity<TeamDTO> updateById(@PathVariable Long id, @RequestBody TeamDTO request){
        TeamDTO response = teamService.updateById(id, request);

        return ResponseEntity
                .ok(response);
    }

    // id, memberDto 를 받아오고 id 에 맞는 member 객체 삭제
    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Void> deleteByID(@PathVariable Long id){
        teamService.deleteById(id);

        return ResponseEntity.ok(null);
    }
}
