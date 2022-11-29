package com.gdsc.jpa.controller;


import com.gdsc.jpa.dto.MemberDto;
import com.gdsc.jpa.entity.Member;
import com.gdsc.jpa.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor //초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성
@RequestMapping("/api") // URL 을 컨트롤러의 메서드와 매핑할 때 사용하는 어노테이션
public class MemberController {

    //의존성 주입
    /* 의존성 주입의 기본적인 의미는 ‘외부'에서 클라이언트에게 서비스를 제공(주입)하는 것
       다시 말해, 객체가 필요로 하는 어떤 것을
       외부에서 전달해주는 것으로 볼 수 있다.*/
    private final MemberService memberService;

    //TeamId값으로 팀 정하고 Member 객체를 생성
    @PostMapping("/teams/{id}/members")
    public ResponseEntity<MemberDto> saveByTeamId(@PathVariable("id")Long id,@RequestBody MemberDto request)
    //요청본문 requestBody, 응답본문 responseBody 을 담아서 보내야 한다.
    // ResponseEntity<MemberDto> -> JSON 형식으로 데이터가 반환
    //URL을 처리할 때는 @PathVariable을 사용

    /**
     * @RequestBody 어노테이션은 HttpRequest의 본문 requestBody의 내용을 자바 객체로 매핑하는 역할
     *
     * 해당하는 어노테이션이 붙어있는 메서드로 클라이언트의 요청이 들어왔을 때,
     * DispatcherServlet에서는 먼저 해당 HttpRequest의 미디어 타입을 확인하고,
     * 타입에 맞는 MessageConverter를 통해 요청 본문인 requestBody를 통째로 변환해서
     * 메서드로 전달해주게 된다.
     */
    {
        MemberDto response = memberService.saveByTeamId(id,request);

        return ResponseEntity.created(URI.create("/api/members/"+response.getId()))
                .body(response);//URI : URI는 특정 리소스를 식별하는 통합 자원 식별자(Uniform Resource Identifier)를 의미
    }

    //{id}팀에 포함된 모든 멤버를 가져오고 List 에 findAllByTeamId(id) 값을 넣어줌
    //url로 호출하여 json 객체로 /teams/{id}/members출력
    @GetMapping("teams/{id}/members")
    public ResponseEntity<List<MemberDto>> findAllByTeamId(@PathVariable("id") Long id){
       // findBy에 이어 해당 Entity 필드 이름을 입력하면 검색 쿼리를 실행한 결과를 전달한다.
       // SQL의 where절을 메서드 이름을 통해 전달한다고 생각하면 된다.
        List<MemberDto> response = memberService.findAllByTeamId(id);

        if(response.isEmpty()){ //팀의 멤버가 비어있다면 (없다면)
            return ResponseEntity
                    .noContent() //에러 발생
                    .build();
        }
        return ResponseEntity.ok(response);// 요청 성공
    }

    /**
     * ResponseEntity는 개발자가 직접 결과 데이터와 HTTP 상태 코드를 직접 제어할 수 있는 클래스이다.
     * 404나 500 ERROR 같은 HTTP 상태 코드를 전송하고 싶은 데이터와 함께 전송할 수 있기 때문에
     * 세밀한 제어가 필요한 경우 사용
     */

    /**
     * 모든 멤버 조회 Controller
     */
    @GetMapping("/members")
    public ResponseEntity<List<MemberDto>> findAll() {
        List<MemberDto> response = memberService.findAll();

        if (response.isEmpty()) {
            return ResponseEntity
                    .noContent()
                    .build();
        }
        return ResponseEntity.ok(response);
    }

    /**
     * 멤버 id로  한 명의 회원 조회 Controller
     */
        @GetMapping("/members/{id}")
        public ResponseEntity<MemberDto> findById(@PathVariable("id") Long id) {//Id값을 이용하여 엔티티를 검색하는 메서드
            MemberDto response = memberService.findById(id);

            return ResponseEntity.ok(response);
        }

    /**
     * 멤버 id로 한 명의 멤버 조회
     * 조회 후 멤버의 name, age를 update 하는 Controller
     */
        @PatchMapping("/members/{id}")
        public ResponseEntity<MemberDto> updateById(@PathVariable("id")Long id,@RequestBody MemberDto request){

            MemberDto response = memberService.updateById(id,request);

            return ResponseEntity.ok(response); //요청이 성공적으로 완료되었다는 의미
        }
    /**
     * 멤버 id로 멤버 삭제 Controller
     */
        @DeleteMapping("/members/{id}")
        public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
            memberService.deleteById(id);

            return ResponseEntity.ok(null);
        }
    }
