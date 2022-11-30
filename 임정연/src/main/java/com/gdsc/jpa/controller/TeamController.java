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
    
    //CRUD
    @PostMapping("/teams") //이름(팀명)으로 팀 생성
    public ResponseEntity<TeamDTO> save(@RequestBody TeamDTO request){
        //TeamDTO으로 반환, TeamDtO라는 객체가 Body 부분에 들어가서 응답으로 전송됨
        TeamDTO response = teamService.save(request);
        return ResponseEntity
                .created(URI.create("/api" + response.getId())) //아이디를 이용하여 URI를 만들어줌
                .body(response);
    }

    @GetMapping("/teams")
    //모든 팀을 조회
    public ResponseEntity<List<TeamDTO>> findAll(){
        List<TeamDTO> responses = teamService.findAll();

        if(responses.isEmpty()){ //저장된 팀이 아무것도 없다면
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity
                .ok(responses);
    }

    @GetMapping("/teams/{id}")
    //해당하는 id의 팀 조회
    public ResponseEntity<TeamDTO> findById(@PathVariable Long id){
        TeamDTO response = teamService.findById(id);
        return ResponseEntity
                .ok(response);
    }

    @DeleteMapping("/teams/{id}")
    //해당하는 id의 팀 삭제
        public ResponseEntity<Void> deleteById(@PathVariable Long id){
            teamService.deleteById(id);

            return ResponseEntity.ok(null);
        }
}
/*
Controller?
-클라이언트에서 요청이 들어올 때, 해당 요청을 수행할 비즈니스 로직을 제어하는 객체
스프링에서는 컨트롤러에서 세부적으로 서비스 레이어를 만들어 해당 요청사항을 객체 지향적인 방식으로 좀 더 세분화하여 관리
 */