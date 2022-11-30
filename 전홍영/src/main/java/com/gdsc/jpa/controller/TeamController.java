package com.gdsc.jpa.controller;

import com.gdsc.jpa.Service.TeamService;
import com.gdsc.jpa.dto.TeamDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TeamController {
    //TeamService 의존성 주입
    private final TeamService teamService;

    /**
     * TeamDto를 파라미터로 받아 저장한다.
     * /api/teams/{id} 를 uri 로 만들어준다.
     * @param request TeamDto
     */
    @PostMapping("/teams")
    public ResponseEntity<TeamDto> save(@RequestBody TeamDto request) {
        TeamDto response = teamService.save(request);
        return ResponseEntity.created(URI.create("/api/" + response.getId()))
                .body(response);
    }

    /**
     * TeamDTo를 모두 조회한다.
     * @return 조회한 결과를 담은 List를 반환한다.
     */
    @GetMapping("/teams")
    public ResponseEntity<List<TeamDto>> findAll() {
        List<TeamDto> responses = teamService.findALl();
        return ResponseEntity
                .ok(responses);
    }

    /**
     * id를 파라미터로 받아 id에 맞는 team을 조회한다.
     * @param id TeamId
     * @return id에 맞는 TeamDto를 반환한다.
     */
    @GetMapping("/teams/{id}")
    public ResponseEntity<TeamDto> findById(@PathVariable Long id) {
        TeamDto response = teamService.findById(id);
        return ResponseEntity
                .ok(response);
    }

    /**
     * id와 teamDto를 파라미터로 받아 id에 맞는 TeamDto를 새로 받은 TeamDto로 update한다.
     * @param id TeamId
     * @param request update할 TeamDto
     * @return update한 TeamDto를 반환한다.
     */
    @PatchMapping("/teams/{id}")
    public ResponseEntity<TeamDto> updateById(@PathVariable("id") Long id, @RequestBody TeamDto request) {
        TeamDto response = teamService.updateById(id, request);
        return ResponseEntity
                .ok(response);
    }

    /**
     * id에 맞는 Team을 삭제한다.
     * @param id TeamId
     * @return team객체를 삭제하고 null을 반환한다.
     */
    @DeleteMapping("teams/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        teamService.deleteById(id);
        return ResponseEntity.ok(null);
    }
}
