package com.gdsc.jpa.controller;

import com.gdsc.jpa.Service.MemberService;
import com.gdsc.jpa.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController//@ResponseBody + @Controller
@RequiredArgsConstructor//final 필드에 대해 생성자를 생성
@RequestMapping("/api")//매핑 어노테이션
public class MemberController {
    //MemberService 의존성 주입
    private final MemberService memberService;

    /**
     * memberDto와 id를 파라미터로 받아 member를 저장한다.
     * @param id member의 id
     * @param request 저장할 memberDto
     * @return /api/members/{id} uri를 반환한다.
     */
    @PostMapping("/teams/{id}/member")
    public ResponseEntity<MemberDto> save(@PathVariable("id") Long id,@RequestBody MemberDto request) {
        MemberDto response = memberService.saveByTeamId(id,request);
        return ResponseEntity.created(URI.create("/api/members" + response.getId()))
                .body(response);
    }

    /**
     * member를 모두 조회한다.
     * @return memberService를 통해 memeber를 모두 조회한 값이 담긴 List를 반환한다.
     * 만약 List가 비어있으면 204(noContent) 에러를 발생시킨다.
     */
    @GetMapping("/members")
    public ResponseEntity<List<MemberDto>> findAll() {
        List<MemberDto> responses = memberService.findAll();
        if (responses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity
                .ok(responses);
    }

    /**
     * Team에 속한 Member를 반환한다.
     * @param id TeamId를 파라미터로 받는다.
     * @return TeamID를 통해 조회한 members를 반환한다.
     * team에 속한 member가 없을경우 204 에러를 발생시킨다.
     */
    @GetMapping("/teams/{id}/members")
    public ResponseEntity<List<MemberDto>> findAllByTeamId(@PathVariable("id") Long id) {
        List<MemberDto> members = memberService.findAllByTeamId(id);
        if (members.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(members);
    }

    /**
     * id에 맞는 member 조회
     * @param id memberId를 파라미터로 받는다.
     * @return 조회한 member를 반환한다.
     */
    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDto> findById(@PathVariable Long id) {
        MemberDto response = memberService.findById(id);
        return ResponseEntity
                .ok(response);
    }

    /**
     * member를 업데이트한다.
     * @param id memberID
     * @param request update할 memberDto
     * @return update한 MemberDto를 반환한다.
     */
    @PatchMapping("/members/{id}")
    public ResponseEntity<MemberDto> updateById(@PathVariable("id") Long id, @RequestBody MemberDto request) {
        MemberDto response = memberService.updateById(id, request);
        return ResponseEntity
                .ok(response);
    }

    /**
     * id에 맞는 member 객체 삭제
     * @param id memberId를 파라미터로 받는다.
     * @return member를 삭제한후 null을 반환한다.
     */
    @DeleteMapping("members/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return ResponseEntity.ok(null);
    }
}
