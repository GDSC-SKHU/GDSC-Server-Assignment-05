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

    //teamDTO를 받아서 teamService.save() 넣어줌
    // /api/teams/{id}를 uri로 생성한다.
    @PostMapping("/teams")
    public ResponseEntity<TeamDTO> save(@RequestBody TeamDTO request){
        TeamDTO response = teamService.save(request);
        return ResponseEntity.created(URI.create("/api/"+response.getId()))
                .body(response);
    }

    //모든 team을 조회하기 위한 controller
    @GetMapping("/teams")
    public ResponseEntity<List<TeamDTO>> findAll(){
        List<TeamDTO> responses = teamService.findAll();

        return ResponseEntity
                .ok(responses);
    }

    //id에 해당하는 team을 조회하기 위한 controller
    //id 값을 받아서 id에 맞는 team 객체를 가져옴
    @GetMapping("/teams/{id}")
    public ResponseEntity<TeamDTO> findById(@PathVariable Long id){
        TeamDTO response = teamService.findById(id);
        return ResponseEntity
                .ok(response);
    }

    //id에 해당하는 팀을 조회 한 뒤 update 하는 controller
    @PatchMapping("/teams/{id}")
    public ResponseEntity<TeamDTO> updateById(@PathVariable Long id, @RequestBody TeamDTO request){
        TeamDTO response = teamService.updateById(id, request);

        return ResponseEntity.ok(response);
    }

    //id에 해당하는 팀을 조회한 뒤 delete 하는 controller
    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        teamService.deleteById(id);
        return ResponseEntity.ok(null);
    }


}
