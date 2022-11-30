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

    @PostMapping("/teams/{id}/members")
    // TeamDTO를 받아와서 Teamservice.save에 저장.
    public ResponseEntity<MemberDTO> save(@PathVariable("id") Long id, @RequestBody MemberDTO request) {
        MemberDTO response = memberService.saveByTeamId(id, request);

        //api/teams 뒤에 id를 받아와서 URI를 만들어줌.
        return ResponseEntity.created(URI.create("/api/teams" + response.getId())).body(response);
    }


    //id에 포함된 모든 멤버를 가지고 와서 return
    @GetMapping("/teams/{id}/members")
    public ResponseEntity<List<MemberDTO>> findAllByTeamId(@PathVariable("id") Long id) {
        List<MemberDTO> responses = (List<MemberDTO>) memberService.findAllByTeamId(id);


        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity.ok(responses);
    }

    //get요청이 들어오면 전체 member의 정보를 return
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

    //Get 요청 시 전달받은 id를 가진 member를 찾아 return
    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDTO> findById(@PathVariable("id") Long id) {
        MemberDTO response = memberService.findById(id);

        return ResponseEntity.ok(response);
    }

    //일치하는 id를 찾으면 수정
    @PatchMapping("/members/{id}")
    public ResponseEntity<MemberDTO> updateById(@PathVariable("id") Long id, @RequestBody MemberDTO request) {
        MemberDTO response = memberService.updateById(id, request);

        return ResponseEntity.ok(response);
    }


    //delete하려는 Id 값을 받으면 찾아서 삭제
    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        memberService.deleteById(id);

        return ResponseEntity.ok(null);
    }


}
