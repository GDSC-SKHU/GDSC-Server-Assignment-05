package com.gdsc.jpa.service;

import com.gdsc.jpa.dto.MemberDTO;
import com.gdsc.jpa.entity.Member;
import com.gdsc.jpa.entity.Team;
import com.gdsc.jpa.repository.MemberRepository;
import com.gdsc.jpa.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service //비즈니스 로직이 들어가는 Service로 사용되는 클래시임을 명시해줌
@RequiredArgsConstructor //final이나 @NonNull인 필드 값만 파라미터로 받는 생성자를 만들어준다
public class MemberService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    //CRUD 구성
    /*
    Transaction이란 어떤 작업에서 오류가 발생했을 때는 이전에 있던 모든 작업들이 성공적이었더라도 없었던 일 처럼 완전히 되돌리는 것
    어떤 작업을 처리하던 중 오류가 발생했을 때 모든 작업들을 원상태로 되돌릴 수 있다
    일련의 작업들을 묶어서 하나의 단위로 처리하고 싶을떄 @Transational을 활용
     */
    @Transactional
    public MemberDTO saveByTeamId(Long teamId, MemberDTO mdto){//멤버 테이블과 팀 테이블 조인시 teamId로 조인함
        //그렇기 떄문에 id로 팀을 찾아 그 팀의 dto로 멤버 생성
        Team team = teamRepository.findById(teamId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 id로 찾을 수 없음"));

        Member member = Member.builder() //그렇기 때문에 MemberService에서  saveByTeamId는 teamId로 팀을 찾고 그 팀에서 입력받은 dto를 기반으로 멤버를 생성한다
                .name(mdto.getName())
                .age(mdto.getAge())
                .team(team)
                .build();
        return memberRepository.save(member).toDTO();
        //save()메소드는 엔티티를 DB에 저장하는 기능을 수행, 바로 DB에 저장되지 않고 영속성 컨텍스트에 저장되었다가 flush()
    }
    @Transactional(readOnly = true)
    //모든 멤버 객체를 리스트로 출력
    public List<MemberDTO> findAll(){
        List<Member> members = memberRepository.findAll();

        return members.stream() //stream메소드는 아래 조건에 부합하는 데이터만 선택
                .map(Member::toDTO)//모든 멤버를 DTO로 바꿔줌
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    //팀 아이디로 팀을 찾아서 그 팀에 해당하는 모든 멤버들에 대한 정보를 ㅜ출력
    public List<MemberDTO> findAllByTeamId(Long teamId){
        Team team = teamRepository.findById(teamId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 ID로 찾을 수 없습니다"));
        List<Member> members = memberRepository.findAllByTeam(team);
        return members.stream() //특정 조건에 부합하는 데이터만 선택
                .map(Member::toDTO)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    //해당하는 id의 멤버 찾기
    public MemberDTO findById(Long id){
        return findEntityById(id).toDTO();
    }

    @Transactional
    //해당 id의 멤버 정보 수정
    public MemberDTO updateById(Long id, MemberDTO mdto) {
        Member member = findEntityById(id);
        member.update(mdto);

        return memberRepository.saveAndFlush(member).toDTO();
        //saveAndFlush()는 즉시 db에 변경사항을 적용하는 방식
    }

    @Transactional
    public void deleteById(Long id){ //아이디로 찾아 멤버 지우기
        Member member = findEntityById(id);
        memberRepository.delete(member);
    }
    @Transactional(readOnly = true) //반복되는 부분 한번에 묶어서 코드 정리
        Member findEntityById(Long id){
            return memberRepository.findById(id)
                    .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
            /*
            ResponseStatusException은 @ResponseStatus의 대체제이다, 비슷한 유형의 예외를 별도로 처리할 수 있고
            응답마다 다른 상태 코드를 세팅할 수 있다
            불필요한 Exception 클래스 생성을 피할 수 있다
            Exception 처리를 추가적인 어노테이션 없이 코드 단에서 자연스럽게 처리
             */

        }

}
