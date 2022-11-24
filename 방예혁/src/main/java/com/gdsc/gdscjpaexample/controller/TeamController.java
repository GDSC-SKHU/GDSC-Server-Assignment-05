package com.gdsc.gdscjpaexample.controller;

import com.gdsc.gdscjpaexample.dto.TeamDTO;
import com.gdsc.gdscjpaexample.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/*
   [TeamController]
   create team    (HTTP method: POST, 주소: /teams)
   get teams(all) (HTTP method: GET, 주소: /teams)
   get team by id (HTTP method: GET, 주소: /teams/{id})
   update team    (HTTP method: PATCH, 주소: /teams/{id})
   delete team    (HTTP method: DELETE, 주소: /teams/{id})
*/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TeamController {

    private final TeamService teamService;

    /* name(팀명)을 이용해 팀 생성함
       /teams 주소로 Post 요청이 들어오면 실행 */
    @PostMapping("/teams")
    public ResponseEntity<TeamDTO> save(@RequestBody TeamDTO request) {
        TeamDTO response = teamService.save(request);

        // 생성한 팀의 id를 이용해 /api/teams/id 처럼 뒤에 붙여서 URI 만들어줌
        return ResponseEntity
                .created(URI.create("/api/teams/" + response.getId()))
                .body(response);
    }

    /* findAll()을 이용해서 현재 DB에 존재하는 모든 팀을 가져와서 리턴
       /teams 주소로 Get 요청이 들어오면 실행 */
    @GetMapping("/teams")
    public ResponseEntity<List<TeamDTO>> findAll() {
        List<TeamDTO> responses = teamService.findAll();

        // 만약 DB에 저장된 팀이 없으면 noContent
        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        // 아니면 팀들 출력
        return ResponseEntity.ok(responses);
    }

    /* id를 통해 팀을 찾고, 그 팀의 정보를 가져와서 리턴
       /teams/(찾으려는 팀의 id)로 get 요청이 들어오면 실행 */
    @GetMapping("/teams/{id}")
    public ResponseEntity<TeamDTO> findById(@PathVariable("id") Long id) {
        TeamDTO response = teamService.findById(id);

        return ResponseEntity.ok(response);
    }

    /* id로 팀을 찾은 다음에 그 팀의 정보(여기선 팀명(name)만)를 업데이트함
       /teams/(업데이트하려는 팀의 id)로 patch 요청이 들어오면 실행 */
    @PatchMapping("/teams/{id}")
    public ResponseEntity<TeamDTO> updateById(@PathVariable("id") Long id, @RequestBody TeamDTO request) {
        TeamDTO response = teamService.updateById(id, request);

        return ResponseEntity.ok(response);
    }

    /* id로 팀을 찾아서 그 팀을 (DB 에서) 제거
       /teams/(삭제하려는 팀의 id)로 delete 요청이 들어오면 실행 */
    @DeleteMapping("/teams/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        teamService.deleteById(id);

        return ResponseEntity.ok(null);
    }
}
