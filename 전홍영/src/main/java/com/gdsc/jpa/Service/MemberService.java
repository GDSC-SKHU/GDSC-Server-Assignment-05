package com.gdsc.jpa.Service;

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

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    //CRUD
    //@Transactional이 붙은 메서드는 메서드가 포함하고 있는 작업 중에 하나라도 실패할 경우 전체 작업을 롤백한다.(https://tecoble.techcourse.co.kr/post/2021-05-25-transactional/)
    @Transactional
    public MemberDto saveByTeamId(Long teamId, MemberDto dto) {//멤버 저장
        //id에 맞는 Team을 찾는다. 만약 팀이 없으면 에러를 발생시킨다.
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, " 팀 ID가 존재하지 않습니다."));
        //MemberDto를 builder로 member 생성
        Member member = Member.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .team(team)
                .build();
        //생성된 member 저장
        return memberRepository.save(member).toDto();
    }
    @Transactional(readOnly = true)//readOnly 속성은 데이터가 변경되는 일을 방지해준다.
    public List<MemberDto> findAllByTeamId(Long teamId) {
        //teamId에 맞는 team을 조회한다.. 오류를 던진다.
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "찾는 팀이 존재하지 않스빈다."));
        //team에 속한 member를 조회한다.
        List<Member> members = memberRepository.findAllByTeam(team);
        //조회한 members를 stream을 통해 MemberDto로 변환 후 반환한다.
        return members.stream()
                .map(Member::toDto)
                .collect(Collectors.toList());

    }
    @Transactional(readOnly = true)//읽기 전용
    public List<MemberDto> findAll() {
        //모든 member를 조회한다.
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(Member::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberDto findById(Long id) {
        //id에 맞는 member를 조회한다.
        Member member = findEntityById(id);
        //member를 MemberDto로 변환 후 반환한다.
        return member.toDto();
    }

    @Transactional
    public MemberDto updateById(Long id, MemberDto dto) {
        //id에 맞는 member를 조회한다.
        Member member = findEntityById(id);
        //해당 member를 받아온 MemberDto로 update한다.
        member.update(dto);
        //update한 member를 저장한다.
        return memberRepository.saveAndFlush(member).toDto();
    }

    @Transactional
    public void deleteById(Long id) {
        //id에 맞는 member를 조회한다.
        Member member = findEntityById(id);
        //해당 member를 삭제한다.
        memberRepository.delete(member);

    }

    private Member findEntityById(Long id) {
        //id에 맞는 member를 조회한다. 찾는 member가 없으면 오류를 던진다.
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FOUND, "해당 ID의 팀이 존재하지 않습니다"));
        return member;
    }

}
