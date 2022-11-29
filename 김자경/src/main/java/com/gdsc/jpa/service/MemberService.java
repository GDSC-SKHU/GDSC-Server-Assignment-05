package com.gdsc.jpa.service;

import com.gdsc.jpa.entity.Member;
import com.gdsc.jpa.entity.Team;
import com.gdsc.jpa.dto.MemberDTO;
import com.gdsc.jpa.repository.MemberRepository;
import com.gdsc.jpa.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service // 핵심 비즈니스 로직을 담은 서비스 클래스를 bean으로 등록
@RequiredArgsConstructor // 클래스에 선언된 final 변수들, 필드들을 매개변수로 하는 생성자를 자동으로 생성
public class MemberService {

    // 생성자를 사용하여 의존성을 주입
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    // CRUD -> Create(생성)
    // 멤버 등록
    // @Transactional : 데이터베이스의 상태를 변경하는 작업 or 한번에 수행되어야 하는 연산들
    // 실행(begin), 종료(commit)을 자동으로 수행하며 예외 발생 시에는 rollback 처리를 자동 수행
    @Transactional
    public MemberDTO saveByTeamId(Long teamId, MemberDTO dto) {
        Team team = teamRepository.findById(teamId)
                // teamId가 없으면 NOT FOUND 오류 발생
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음"));

        // teamId를 이용하여 team 조회한 후 이름(name), 나이(age) 등록
        Member member = Member.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .team(team)
                .build();
        // memberRepository에 member 정보 저장
        return memberRepository.save(member).toDTO();
    }

    // CRUD -> Read(읽기)
    // 멤버 전체 조회
    // readOnly = true : 성능 최적화 및 실수로 데이터를 변경하는 일을 방지
    @Transactional(readOnly = true)
    public List<MemberDTO> findAll() {
        List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(Member::toDTO)
                .collect(Collectors.toList());
    }

    // Team id로 조회
    @Transactional(readOnly = true)
    public List<MemberDTO> findAllByTeamId(Long teamId) {
        Team team = teamRepository.findById(teamId)
                // teamId가 없으면 NOT FOUND 오류 발생
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음"));

        List<Member> members = memberRepository.findAllByTeam(team);

        return members.stream()
                .map(Member::toDTO)
                .collect(Collectors.toList());
    }

    // Member id로 조회
    @Transactional(readOnly = true)
    public MemberDTO findById(Long id) {
        return findEntityById(id).toDTO();
    }

    // CRUD -> Update(갱신)
    @Transactional
    public MemberDTO updateById(Long id, MemberDTO dto) {
        Member member = findEntityById(id);
        member.update(dto);
        // save() : 영속성 컨텍스트에 저장하는 것이고 실제로 DB 에 저장은 추후 flush 또는 commit 메소드가 실행될 때 이루어짐
        // saveAndFlush() : 즉시 DB에 데이터를 반영
        return memberRepository.saveAndFlush(member).toDTO();
    }

    // CRUD -> Delete(삭제)
    @Transactional
    public void deleteById(Long id) {
        Member member = findEntityById(id);
        memberRepository.delete(member);
    }

    // CRUD -> Read(읽기)
    // id로 member 조회
    @Transactional(readOnly = true)
    Member findEntityById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Member 찾을 수 없음"));
    }
}