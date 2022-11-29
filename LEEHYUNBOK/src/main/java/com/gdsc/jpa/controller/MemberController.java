package com.gdsc.jpa.controller;


import com.gdsc.jpa.dto.MemberDTO;
import com.gdsc.jpa.entity.Member;
import com.gdsc.jpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
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
    public ResponseEntity<MemberDTO> save(@PathVariable("id") Long teamId, @RequestBody MemberDTO request) {

        MemberDTO response = memberService.saveByTeamId(teamId, request);

        return ResponseEntity.created(URI.create("/api/members/"+response.getId()))
                .body(response);

    }

    @GetMapping("/teams/{id}/members")
    public ResponseEntity<List<MemberDTO>> findAllByTeamId(@PathVariable("id") Long teamId){
        List<MemberDTO> responses = memberService.findAllByTeamId(teamId);

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .ok(responses);
    }


    @GetMapping("/members")
    public ResponseEntity<List<MemberDTO>> findAll(){
        List<MemberDTO> responses = memberService.findAll();

        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity
                .ok(responses);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDTO> findById(@PathVariable("id") Long id){
        MemberDTO response = memberService.findById(id);

        return ResponseEntity
                .ok(response);
    }

    @PatchMapping("/members/{id}")
    public ResponseEntity<MemberDTO> updateById(@PathVariable("id") Long id, @RequestBody MemberDTO request){
        MemberDTO response = memberService.updateById(id,request);

        return ResponseEntity
                .ok(response);
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id){
        memberService.deleteById(id);

        return ResponseEntity
                .ok(null);
    }


}
