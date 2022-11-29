package com.gdsc.jpa.controller;

import com.gdsc.jpa.dto.MemberDTO;
import com.gdsc.jpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController // json형태로 객체 데이터 반환
@RequiredArgsConstructor // final이 붙은 필드의 생성자 자동 생성
@RequestMapping("/api") // api 주소가 붙음
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/teams/{id}/members") // id가 들어왔을 때 id를 db에 저장
    // pathvariable을 사용하여 url에서 id에 들어오는 값 처리(저장)
    public ResponseEntity<MemberDTO> saveByTeamId(@PathVariable("id") Long id, @RequestBody MemberDTO request) {
        MemberDTO response = memberService.saveByTeamId(id, request); // team에 id 저옴

        return ResponseEntity.created(URI.create("/api/members/" + response.getId()))
                .body(response); // /api/members/id 의 Url을 생성해줌
    }

    @GetMapping("/teams/{id}/members") // id가 url에 들어왔을 때
    // url에 id가 들어왔을 때 팀에 있는 id를 find 해줌
    public ResponseEntity<List<MemberDTO>> findAllByTeamId(@PathVariable("id") Long id) {
        List<MemberDTO> responses = memberService.findAllByTeamId(id); // 모든 id를 찾음

        if (responses.isEmpty()) { // id가 없을 때
            return ResponseEntity
                    .noContent() // 내용이 없음
                    .build();
        }

        return ResponseEntity.ok(responses); // id가 있을 때 찾은 값 모두 리턴
    }

    @GetMapping("/members") // url에 들어옴
    public ResponseEntity<List<MemberDTO>> findAll() { // DTO 값 반환. 모두 찾아줌
        List<MemberDTO> responses = memberService.findAll(); // memberService에 있는 값을 모두 찾아 List형식으로 반환

        if (responses.isEmpty()) { // 값이 없을 때
            return ResponseEntity
                    .noContent() // 내용이 없음
                    .build();
        }

        return ResponseEntity.ok(responses); // 값이 있을 때 모두 반환
    }

    @GetMapping("/members/{id}") // id가 url에 들어왔을 때
    // pathvariable을 사용하여 url에서 id에 들어오는 값 처리
    public ResponseEntity<MemberDTO> findById(@PathVariable("id") Long id) { // url에 들어온 id를 찾아줌
        MemberDTO response = memberService.findById(id);

        return ResponseEntity.ok(response); // id 리턴하기
    }


    @PatchMapping("/members/{id}") // 리소스 업데이트. url로 들어온 id의 값을 바꿔줌
    public ResponseEntity<MemberDTO> updateById(@PathVariable("id") Long id, @RequestBody MemberDTO request) {
        MemberDTO response = memberService.updateById(id, request); // id값을 업데이트 시켜줌

        return ResponseEntity.ok(response); // 위의 값 반환(바뀐 값)
    }

    @DeleteMapping("/members/{id}") // url로 들어온 id
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) { // id 삭제 메소드
        memberService.deleteById(id); // id를 삭제해줌

        return ResponseEntity.ok(null); // 삭제된 id이므로 null 값 반환
    }
}