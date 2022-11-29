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

    // 전달받은 id의 팀 멤버 저장
    @PostMapping("/teams/{id}/members")
    public ResponseEntity<MemberDTO> saveByTeamId(@PathVariable("id") Long id, @RequestBody MemberDTO request) {
        MemberDTO response = memberService.saveByTeamId(id, request);

        return ResponseEntity
                .created(URI.create("/api/members/" + response.getId()))
                .body(response);
    }

    // 멤버 전체 조회
    @GetMapping("/members")
    public ResponseEntity<List<MemberDTO>> findAll() {
        List<MemberDTO> responses = memberService.findAll();

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .ok(responses);
    }

    // 전달받은 id의 팀 멤버 전체 조회
    @GetMapping("/teams/{id}/members")
    public ResponseEntity<List<MemberDTO>> findAllByTeamId(@PathVariable("id") Long id) {
        List<MemberDTO> responses = memberService.findAllByTeamId(id);

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .ok(responses);
    }


    // 전달받은 id의 멤버 조회
    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDTO> findById(@PathVariable("id") Long id) {
        MemberDTO response = memberService.findById(id);

        return ResponseEntity
                .ok(response);
    }

    // 멤버 정보 갱신
    @PatchMapping("/members/{id}")
    public ResponseEntity<MemberDTO> updateById(@PathVariable("id") Long id, @RequestBody MemberDTO request) {
        MemberDTO response = memberService.updateById(id, request);

        return ResponseEntity
                .ok(response);
    }

    // 멤버 삭제
    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        memberService.deleteById(id);

        return ResponseEntity
                .ok(null);
    }
}
