package com.gdsc.jpa.controller;

import com.gdsc.jpa.dto.MemberDTO;
import com.gdsc.jpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    // id(teamId) 값을 통해 Member 객체를 build 함
    // id(teamId), MemberDto 파라미터를 받아와서 builder 를 통해  memberService.saveByTeamId(id, request) 을 만듬
    // /api/members/{id} uri 를 결과값으로 보낸다.
    @PostMapping("/teams/{id}/members")
    public ResponseEntity<MemberDTO> saveByTeamId(@PathVariable("id") Long id, @RequestBody MemberDTO request) {
        MemberDTO response = memberService.saveByTeamId(id, request);

        return ResponseEntity.created(URI.create("/api/members/" + response.getId()))
                .body(response);
    }

    // id를 받아오고 List 에 findAllByTeamId(id) 값을 넣어준다.
    // List 가 비어 있을 경우 noContent => 201 에러 발생시킴
    // 비어 있지 않으면 200 요청 성공 코드 반환
    @GetMapping("/teams/{id}/members")
    public ResponseEntity<List<MemberDTO>> findAllByTeamId(@PathVariable("id") Long id) {
        List<MemberDTO> responses = memberService.findAllByTeamId(id);

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity.ok(responses);
    }

    // memberService.findAll() 값을 responses에 넣어준다.
    // List 가 비어 있을 경우 noContent => 201 에러 발생시킴
    // 비어 있지 않으면 200 요청 성공 코드 반환
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



    // id 값을 받고 id에 맞는 findById 를 통해 member 객체를 받아옴
    // 200 요청 성공 코드 반환
    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDTO> findById(@PathVariable("id") Long id) {
        MemberDTO response = memberService.findById(id);

        return ResponseEntity.ok(response);
    }

    // id, memberDto 를 받아오고 id 에 맞는 member 객체 수정
    @PatchMapping("/members/{id}")
    public ResponseEntity<MemberDTO> updateById(@PathVariable("id") Long id, @RequestBody MemberDTO request) {
        MemberDTO response = memberService.updateById(id, request);

        return ResponseEntity.ok(response);
    }

    // id, memberDto 를 받아오고 id 에 맞는 member 객체 삭제
    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        memberService.deleteById(id);

        return ResponseEntity.ok(null);
    }
}