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

    // Create
    @Transactional
    public MemberDTO saveByTeamId(Long teamId, MemberDTO dto) {
        // 에러 처리
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음"));

        // 빌더
        Member member = Member.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .team(team)
                .build();

        return memberRepository.save(member).toDTO();       // DTO로 저장
    }


    // Read
    // 읽기전용
    @Transactional(readOnly = true)
    public List<MemberDTO> findAll() {
        List<Member> members = memberRepository.findAll();

        // 스트림
        return members.stream()
                .map(Member::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MemberDTO> findAllByTeamId(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음"));

        List<Member> members = memberRepository.findAllByTeam(team);

        return members.stream()
                .map(Member::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberDTO findById(Long id) {
        return findEntityById(id).toDTO();
    }

    @Transactional(readOnly = true)
    Member findEntityById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Member 찾을 수 없음"));
    }


    // Update
    @Transactional
    public MemberDTO updateById(Long id, MemberDTO dto) {
        Member member = findEntityById(id);
        member.update(dto);

        return memberRepository.saveAndFlush(member).toDTO();
    }


    // Delete
    @Transactional
    public void deleteById(Long id) {
        Member member = findEntityById(id);
        memberRepository.delete(member);
    }

}
