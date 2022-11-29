package com.gdsc.jpa.controller;

import com.gdsc.jpa.dto.MemberDTO;
import com.gdsc.jpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController // Json 형태로 객체 데이터를 반환
@RequiredArgsConstructor // 생성자 주입
@RequestMapping("/api") // 'localhost:8080/api' 형태로 매핑 됨
public class MemberController {
    private final MemberService memberService;

    // id를 이용하여 Team 구성 후 멤버 객체 생성
    // @PathVariable : URL 경로에 변수를 넣어주는 것 -> null이나 공백값이 들어가는 parameter라면 적용하지 않도록 할 것!
    // @RequestBody : HTTP 요청의 body 내용을 통째로 자바객체로 변환해서 매핑된 메소드 파라미터로 전달 -> xml이나 json기반의 메시지를 사용하는 요청의 경우 이 방법이 매우 유용
    @PostMapping("/teams/{id}/members")
    // 컨트롤러를 통해 객체를 반환할 때에는 일반적으로 ResponseEntity로 감싸서 반환
    public ResponseEntity<MemberDTO> saveByTeamId(@PathVariable("id") Long id, @RequestBody MemberDTO request) {
        MemberDTO response = memberService.saveByTeamId(id, request);
        return ResponseEntity.created(URI.create("/api/members/" + response.getId()))
                .body(response);
    }

    // id를 이용하여 팀에 있는 member 조회 후 List에 해당 id 값 삽입
    @GetMapping("/teams/{id}/members")
    public ResponseEntity<List<MemberDTO>> findAllByTeamId(@PathVariable("id") Long id) {
        List<MemberDTO> responses = memberService.findAllByTeamId(id);
        // 만약 비어있으면 No Content
        // 201(No Content) : body에 응답 내용이 없을 경우 이용
        if (responses.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
        // 비어있지 않으면 요청 성공
        // 200(OK) : 요청 성공의 기본 상태 코드
        return ResponseEntity.ok(responses);
    }

    // 모든 member 조회
    @GetMapping("/members")
    public ResponseEntity<List<MemberDTO>> findAll() {
        List<MemberDTO> responses = memberService.findAll();
        // 만약 비어있으면 No Content
        // 201(No Content) : body에 응답 내용이 없을 경우 이용
        if (responses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        // 비어있지 않으면 요청 성공
        // 200(OK) : 요청 성공의 기본 상태 코드
        return ResponseEntity.ok(responses);
    }

    // id를 이용하여 한 명의 member 조회
    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDTO> findById(@PathVariable("id") Long id) {
        MemberDTO response = memberService.findById(id);
        return ResponseEntity.ok(response);
    }

    // PUT : 리소스의 모든 것을 업데이트
    // PATCH : 리소스의 일부를 업데이트
    // id를 이용하여 한 명의 member 조회한 후 이름(name)과 나이(age) 변경
    @PatchMapping("/members/{id}")
    public ResponseEntity<MemberDTO> updateById(@PathVariable("id") Long id, @RequestBody MemberDTO request) {
        MemberDTO response = memberService.updateById(id, request);
        return ResponseEntity.ok(response);
    }

    // id를 이용하여 한 명의 member 조회한 후 삭제
    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        memberService.deleteById(id);
        return ResponseEntity.ok(null);
    }
}
