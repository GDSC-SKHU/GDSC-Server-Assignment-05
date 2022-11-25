package com.gdsc.gdscjpaexample.controller;

import com.gdsc.gdscjpaexample.dto.MemberDTO;
import com.gdsc.gdscjpaexample.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/*
   [MemberController]
   create member to team (HTTP method: POST, 주소: /teams/{id}/members)
   get members by team   (HTTP method: GET, 주소: /teams/{id}/members)
   get members(all)      (HTTP method: GET, 주소: /members)
   get member by id      (HTTP method: GET, 주소: /members/{id})
   update member         (HTTP method: PATCH, 주소: /members/{id})
   delete member         (HTTP method: DELETE, 주소: /members/{id})
*/

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    /* 먼저{id}로 팀을 정하고 그 팀에 name(이름)과 age(나이)를 이용해 멤버 생성
       /api/members/ 주소로 Post 요청이 들어오면 실행 */
    @PostMapping("/teams/{id}/members")
    public ResponseEntity<MemberDTO> saveByTeamId(@PathVariable("id") Long id, @RequestBody MemberDTO request) {
        MemberDTO response = memberService.saveByTeamId(id, request);

        // 생성한 멤버의 id를 이용해 /api/members/id 처럼 뒤에 붙여서 URI 만들어줌
        return ResponseEntity.created(URI.create("/api/members/" + response.getId()))
                .body(response);
    }

    /* findAllByTeamId() 을 이용해서 DB에 {id}팀에 포함된 모든 멤버를 가져와서 리턴
      /teams/{id}/members 주소로 Get 요청이 들어오면 실행 */
    @GetMapping("/teams/{id}/members")
    public ResponseEntity<List<MemberDTO>> findAllByTeamId(@PathVariable("id") Long id) {
        List<MemberDTO> responses = memberService.findAllByTeamId(id);

        // 만약 DB에 지정한 팀의 멤버가 없다면
        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        // 있다면 리턴
        return ResponseEntity.ok(responses);
    }

    /* findAll()을 이용해서 현재 DB에 존재하는 모든 멤버를 가져와서 리턴
       /members 주소로 Get 요청이 들어오면 실행 */
    @GetMapping("/members")
    public ResponseEntity<List<MemberDTO>> findAll() {
        List<MemberDTO> responses = memberService.findAll();

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity.ok(responses);
    }

    /* id를 통해 멤버를 찾고, 그 멤버의 정보를 가져와서 리턴
       /members/(찾으려는 멤버의 id)로 get 요청이 들어오면 실행 */
    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDTO> findById(@PathVariable("id") Long id) {
        MemberDTO response = memberService.findById(id);

        return ResponseEntity.ok(response);
    }

    /* id로 멤버를 찾은 다음에 그 멤버의 정보(여기선 name, age)를 업데이트함
       /teams/(업데이트하려는 멤버의 id)로 patch 요청이 들어오면 실행 */
    @PatchMapping("/members/{id}")
    public ResponseEntity<MemberDTO> updateById(@PathVariable("id") Long id, @RequestBody MemberDTO request) {
        MemberDTO response = memberService.updateById(id, request);

        return ResponseEntity.ok(response);
    }

    /* id로 멤버를 찾아서 그 멤버를 (DB 에서) 제거
       /members/(삭제하려는 멤버의 id)로 delete 요청이 들어오면 실행 */
    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        memberService.deleteById(id);

        return ResponseEntity.ok(null);
    }
}
