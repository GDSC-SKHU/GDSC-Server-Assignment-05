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

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    //멤버 생성
    @Transactional
    public MemberDTO saveByTeamId(Long teamId, MemberDTO dto) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 팀을 찾을 수 없습니다."));

        Member member = Member.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .team(team)
                .build();

        memberRepository.save(member);

        return member.toDTO();
    }

    //모든 정보를 list에 담아 return
    @Transactional(readOnly = true)
    public List<MemberDTO> findAll() {
        List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(Member::toDTO)
                .collect(Collectors.toList()); //stream -> List 형태로 변환
    }

    //모든 team 중에서 id로 팀을 찾아 팀의 속하는 멤버를 list 담아 return
    @Transactional
    public MemberDTO findAllByTeamId(Long teamId) {

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 팀이 존재하지 않습니다."));

        List<Member> members = memberRepository.findAllByTeam(team);

        return (MemberDTO) members.stream()
                .map(Member::toDTO)
                .collect(Collectors.toList());
    }

    //멤버 중 매개변수의 id와 일치한 id를 가진 멤버를 return
    @Transactional(readOnly = true)
    public MemberDTO findById(Long id) {
        return findEntityById(id).toDTO();
    }

    //멤버 중 매개변수의 id와 일치한 id를 가진 멤버를 수정 후 return
    @Transactional
    public MemberDTO updateById(Long id, MemberDTO dto) {
        Member member = findEntityById(id);
        member.update(dto);

        return memberRepository.saveAndFlush(member).toDTO();
    }

    //delete
    @Transactional
    public void deleteById(Long id) {
        Member member = findEntityById(id);
        memberRepository.delete(member);
    }

    //db에서 id로 멤버를 찾아내고 없으면 에러메세지 출력
    @Transactional(readOnly = true)
    Member findEntityById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID와 일치하는 멤버를 찾을 수 없습니다."));
    }
}
