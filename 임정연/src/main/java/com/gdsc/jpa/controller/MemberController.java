package com.gdsc.jpa.controller;


import com.gdsc.jpa.dto.MemberDTO;
import com.gdsc.jpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController //view가 필요없은 API만 지원하는 서비스에서 사용,RequystMapping메서드가 기본적으로 ResponseBody의미를 가정
@RequiredArgsConstructor  //초기화 되자읂은 final필드나, NonNull이 붙은 필드에 대해 생성를 생성해 줌, 주로 의존성 주입 편의성을 위해서 사용됨
@RequestMapping("/api") //클라이언트에게 요청받는 주소를 클래스와 연결시켜주는 어노테이션
public class MemberController {
    /*
    의존성 주입 - 어떠한 빈에 새성자가 오직 하나만 있고, 생성자의 파라미터 타입이 빈으로 등록 가능한 존재라면 이 빈은 @Autowired없이 의존성 주입 가능
    두 객체 간의 관계라는 관심사의 분리
    두 객체 간의 결합도를 낮춤
    객체의 유연성을 높이고 테스트 작성을 용이하게 함
     */
    /*
     ResponseEntity는 결과 데이터와 HTTP 상태코드를 직접 제어할 수 있는 클래스
     사용자의 HttpResuqest에 대한 응답 데이터를 포함하는 클래스
     status field를 가지기 때문에 상태코드는 필수적으로 리턴해줘야 함
     데이터만 리턴해주어도 정상작동하지만, 상태코드, 헤더 등의 부가 정보도 함께 보내주기 위해 사용

     */
    private final MemberService memberService; //의존성 주입

    @PostMapping("/team/{id}/members") //팀 아이디로 멤버 객체 생성
    public ResponseEntity<MemberDTO> save(@PathVariable Long id, @RequestBody MemberDTO request){
        //@PathVariable은 URL 경로 주소에 사용하는 값을 매개 변수로 사용 가능하게 해줌
        //@ResponseBody는 자바 객체를 HTTP 요청의 body 내용을 변환하고 매핑하는 어노테이션
        //@RequestBody는 HTTP 요청의 body 내용을 전달받ㅇ ㅏ자바 객체로 변환/매핑하는 어노테이션

        MemberDTO response = memberService.saveByTeamId(id, request);  //팀 아이디로 멤버 객체 response에 저장

        return ResponseEntity.created(URI.create("/api/members" + response.getId())) //
                .body(response);
    }
     @GetMapping("/teams/{id}/members") //DB에 {id}팀에 포함된 모든 멤버를 가져온다
    public ResponseEntity<List<MemberDTO>> findAllByTeamId(@PathVariable("id") Long id){
         List<MemberDTO> responses = memberService.findAllByTeamId(id);
         if(responses.isEmpty()){ //해당 팀에 멤버가 없으면
             return ResponseEntity
                     .noContent()
                     .build();
         }
         return ResponseEntity.ok(responses); //있다면 요청을 성공
     }

     @GetMapping("/members")
     //멤버를 모두 조회
     public ResponseEntity<List<MemberDTO>> findAll(){
        List<MemberDTO> responses = memberService.findAll();

        if(responses.isEmpty()){
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity.ok(responses);
     }

     @GetMapping("/members/{id}")
     //해당 id에 대한 멤버 조회 (1명)
     public ResponseEntity<MemberDTO> findById(@PathVariable("id") Long id){
        MemberDTO response = memberService.findById(id);

        return ResponseEntity.ok(response);
     }
     @PatchMapping("/members/{id}")
     //해당 id의 멤버 내용을 수정, db 갱신
    public ResponseEntity<MemberDTO> updateById(@PathVariable("id") Long id, @RequestBody MemberDTO request){
        MemberDTO response = memberService.updateById(id, request);
        return ResponseEntity.ok(response);
     }

     @DeleteMapping("/members/{id}")
     //해당 id의 멤버 객체를 삭제
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id){
        memberService.deleteById(id);
        return ResponseEntity.ok(null);
     }
}
/*
Controller?
-클라이언트에서 요청이 들어올 때, 해당 요청을 수행할 비즈니스 로직을 제어하는 객체
스프링에서는 컨트롤러에서 세부적으로 서비스 레이어를 만들어 해당 요청사항을 객체 지향적인 방식으로 좀 더 세분화하여 관리
 */
