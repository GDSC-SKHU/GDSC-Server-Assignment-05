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

@Service
@RequiredArgsConstructor
public class MemberService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    // @Transactional = 메소드가 포함하고 있는 작업 중에 하나라도 실패할 경우 전체 작업을 취소
    @Transactional
    public MemberDTO saveByTeamId(Long teamId, MemberDTO dto) {
        // teamId 가 없을 시 오류
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음"));

        // Member member = new Member(dto.getName(), dto.getAge(),team) 하고 같은 느낌이다.
        Member member = Member.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .team(team)
                .build();

        return memberRepository.save(member).toDto();
    }

    // @Transactional(readOnly = true) = 읽기전용 쿼리
    @Transactional(readOnly = true)
    public List<MemberDTO> findAll() {
        // 모든 멤버 찾기
        List<Member> members = memberRepository.findAll();

        //
        return members.stream()    // 멤버 리스트에서 스트림을 얻음
                .map(Member::toDto)
                .collect(Collectors.toList());  // 스트림을 리스트 형태로 변경
    }

    @Transactional(readOnly = true)
    public List<MemberDTO> findAllByTeamId(Long teamId) {

        // teamId로 찾기
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음"));

        // 팀으로 멤버 찾기
        List<Member> members = memberRepository.findAllByTeam(team);

        return members.stream()
                .map(Member::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberDTO findById(Long id) {
        return findEntityById(id).toDto();
    }

    // id 로 멤버 객체 변경
    @Transactional
    public MemberDTO updateById(Long id, MemberDTO dto) {
        Member member = findEntityById(id);
        member.update(dto);

        return memberRepository.saveAndFlush(member).toDto();
    }

    //  id 로 멤버 객체 삭제
    @Transactional
    public void deleteById(Long id) {
        Member member = findEntityById(id);
        memberRepository.delete(member);
    }


    @Transactional(readOnly = true)
    Member findEntityById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Member 찾을 수 없음"));
    }
}