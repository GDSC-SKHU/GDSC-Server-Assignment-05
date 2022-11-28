package com.gdsc.jpa.controller;

import com.gdsc.jpa.dto.TeamDTO;
import com.gdsc.jpa.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController // json형태로 객체 데이터 반환
@RequiredArgsConstructor // final이 붙은 필드의 생성자 자동 생성
@RequestMapping("/api")
public class TeamController {
    private final TeamService teamService;

    @PostMapping("/items") // items가 들어왔을 때 items를 db에 저장
    // pathvariable을 사용하여 url에서 items에 들어오는 값 처리(저장)
    public ResponseEntity<TeamDTO> save(@RequestBody TeamDTO request){
        TeamDTO response = teamService.save(request); // 값 저장해줌

        return ResponseEntity.created(URI.create("/api"+response.getId()))
                .body(response); // 특정 오류 없을 때 반환이 됨
    }

    @GetMapping("/teams") // teams가 url에 들어왔을 때
    public ResponseEntity<List<TeamDTO>> findAll(){ //
        // url에 temams가 들어왔을 때 팀을 모두 find 해줌
        List<TeamDTO> responses = teamService.findAll();

        return ResponseEntity
                .ok()
                .body(responses); // 모두 불러와짐
    }

    @GetMapping("/teams/{id}") // url에 id가 들어
    // pathvariable을 사용하여 url에서 id에 들어오는 값 처리
    public ResponseEntity<TeamDTO> findById(@PathVariable Long id){
        TeamDTO response = teamService.findById(id); // url에 들어온 Id를 찾아줌
        return ResponseEntity
                .ok(response); // 찾아온 id를 리턴해줌
    }

    @PatchMapping("/teams/{id}") // 리소스 업데이트. url로 들어온 id의 값을 바꿔줌
    public ResponseEntity<TeamDTO> updateById(@PathVariable Long id, @RequestBody TeamDTO request){
        TeamDTO response = teamService.updateById(id, request);

        return ResponseEntity
                .ok(response); // 업데이트된 id 반환(오류가 없을 때)
    }

    @DeleteMapping("/teams/{id}") // url로 id가 들어옴
    public ResponseEntity<Void> deleteById(@PathVariable Long id){ // url에 들어온 Id delete
        teamService.deleteById(id);

        return ResponseEntity.ok(null); // id가 삭제됐으므로 Null 처리
    }

}
