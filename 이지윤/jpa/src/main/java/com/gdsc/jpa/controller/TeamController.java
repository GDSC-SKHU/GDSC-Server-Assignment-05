package com.gdsc.jpa.controller;

import com.gdsc.jpa.dto.TeamDTO;
import com.gdsc.jpa.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController //리턴 값에 자동으로 @ResponseBody가 붙어서 응답 데이터에 객체가 매핑되어 전달됨.
@RequiredArgsConstructor
@RequestMapping("/api")
public class TeamController {

    private final TeamService teamService;

    //POST - /api/teams
    @PostMapping("/teams")
    // TeamDTO를 받아와서 Teamservice.save에 저장.
    public ResponseEntity<TeamDTO> save(@RequestBody TeamDTO request) {
        TeamDTO response = teamService.save(request);

        //api/teams 뒤에 id를 받아와서 URI를 만들어줌.
        return ResponseEntity.created(URI.create("/api/teams" + response.getId())).body(response);
    }

    //teams가 호출되면 findAll()을 이용해서 전체 team을 보여줌.
    @GetMapping("/teams")
    public ResponseEntity<List<TeamDTO>> findAll() {
        List<TeamDTO> responses = teamService.findAll();

        //만약 비어있으면 noContent
        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity.ok(responses); //아니면 ok
    }

    //id를 불러와서 호출되면
    @GetMapping("/teams/{id}")
    //id에 맞는 team을 객체로 받아옴.
    public ResponseEntity<TeamDTO> findById(@PathVariable Long id) { // @PathVariable: id를 파라미터로 받아 사용.
        TeamDTO response = teamService.findById(id);

        return ResponseEntity.ok(response);
    }
    @PatchMapping("/teams/{id}") //id로 team을 찾아 정보 업데이트 시 사용.
    public ResponseEntity<TeamDTO> updateById(@PathVariable Long id, @RequestBody TeamDTO request) {
        TeamDTO response = teamService.updateById(id, request);

        return ResponseEntity.ok(response);
    }

    //id가 들어오면 팀을 찾아 db에서 삭제
    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        teamService.deleteById(id);

        return ResponseEntity.ok(null);
    }

}
