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
// RequestMapping 어노테이션 :  /api/ ~~~ 이렇게 만들어 줌
@RequestMapping("/api")
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/teams")
    public ResponseEntity<TeamDTO> save(@RequestBody TeamDTO request){
        TeamDTO response = teamService.save(request);

        return ResponseEntity.created(URI.create("/api/"+response.getId()))
                .body(response);
    }

    @GetMapping("/teams")
    public ResponseEntity<List<TeamDTO>> findAll(){
        List<TeamDTO> response = teamService.findAll();

        return ResponseEntity
                .ok(response);
    }

    @GetMapping("/teams/{id}")
    public ResponseEntity<TeamDTO> findById(@PathVariable Long id){
        TeamDTO response = teamService.findById(id);

        return ResponseEntity
                .ok(response);
    }

    @PatchMapping("teams/{id}")
    public ResponseEntity<TeamDTO> updateById(@PathVariable Long id, @RequestBody TeamDTO request){
        TeamDTO response = teamService.updateById(id,request);

        return ResponseEntity
                .ok(response);
    }

    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        teamService.deleteById(id);

        return ResponseEntity
                .ok(null);
    }
}
