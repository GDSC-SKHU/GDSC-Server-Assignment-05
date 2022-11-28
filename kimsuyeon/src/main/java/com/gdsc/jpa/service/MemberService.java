package com.gdsc.jpa.service;

import com.gdsc.jpa.dto.MemberDto;
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

@Service //서비스 등록
@RequiredArgsConstructor //final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class MemberService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    /**
     *
     * @Transactional
     *JPA를 사용한다면, 서비스(Service) 클래스에서 필수적으로 사용되어야 하는 어노테이션이다.
     * 일반적으로 메서드 레벨에 선언하게 되며, 메서드의 실행, 종료, 예외를 기준으로
     *각각 실행(begin), 종료(commit), 예외(rollback)를 자동으로 처리
     */
    @Transactional
    public MemberDto saveByTeamId(Long teamId, MemberDto dto) {
        Team team = teamRepository.findById(teamId)// teamId 로 team 찾기
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음"));
        //없으면 Exception(예외)

        //팀에 입력받은 dto 기반으로 create member
        Member member = Member.builder()
                .name(dto.getName())//getName()으로 이름 꺼내기
                .age(dto.getAge())//getAge()로 나이 꺼내기
                .team(team)
                .build();

        return memberRepository.save(member).toDTO();
    }

    @Transactional(readOnly = true)
    public List<MemberDto> findAll() {
        List<Member> members = memberRepository.findAll(); //member테이블에있는 member의 모든 레코드를 member 라는 엔티티로 리스트에 넣어줌

        return  members.stream()// members List객체의 스트림을 연다.
                .map(Member::toDTO)//Member의 toDTO메소드로 Member클래스를 MemberDTO로 변환한다.
                .collect(Collectors.toList());// 스트림을 List 변환하여 반환한다.
    }

    @Transactional(readOnly = true)// 읽기전용 트랜잭션 설정
    public List<MemberDto> findAllByTeamId(Long teamId) {//Team컬럼 대상으로 모든 id조회
        Team team = teamRepository.findById(teamId)  // teamId 로 team 인스턴스 획득
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음"));
        //없으면 Exception
        List<Member> members = memberRepository.findAllByTeam(team);// 위에서 획득한  team으로 member 정보 획득

        return  //stream에 Member가 담기고  map 입장에서는 Member를 한 건씩 꺼내옴
                members.stream()
                .map(Member::toDTO)// Member 인스턴스를 DTO 로 변환
                .collect(Collectors.toList());// Member 여러개를 리스트로 Collect
    }

    @Transactional(readOnly = true)
    public MemberDto findById(Long id) {//id로 member찾기

        return findEntityById(id).toDTO();//id로 찾아 그 리턴값을 DTO로 바꿔서 반환
    }

    @Transactional
    public MemberDto updateById(Long id, MemberDto dto) {
        Member member = findEntityById(id);
        member.update(dto);

        return memberRepository.saveAndFlush(member).toDTO();
        //id로 멤버를 찾고 dto로 바꿔서 update 후 리턴
    }

    @Transactional
    public void deleteById(Long id) { //member id와 매개변수 id가 일치하면 삭제
        Member member = findEntityById(id);
        memberRepository.delete(member);
    }

    @Transactional(readOnly = true)
    Member findEntityById(Long id) {//Id을 기반으로 실제 DB에 있는 영속성 엔티티를 찾아옴
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Member 찾을 수 없음"));
    }
}
