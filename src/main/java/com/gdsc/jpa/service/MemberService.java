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


    @Transactional
    //메소드가 포함하고 있는 작업 중에 하나라도 실패할 경우 전체 작업을 자동으로 처리(실행,종료,예외)하는 어노테이션
    //teamId 로 team 찾기
    public MemberDTO saveByTeamId(Long teamId, MemberDTO dto) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음"));
        //없으면 Exception(예외)

        //dto 받은 걸 기반으로 member 생성
        Member member = Member.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .team(team)
                .build();

        return memberRepository.save(member).toDTO();
    }

    //모든 멤버 검색
    @Transactional(readOnly = true) // 읽기전용
    public List<MemberDTO> findAll() {
        List<Member> members = memberRepository.findAll();

        return  members.stream()// members List에서 스트림을 얻어서
                .map(Member::toDTO)//toDTO method로 Member를 MemberDTO로 변환
                .collect(Collectors.toList());// 스트림을 List 형태로 변경
    }

    //Team 컬럼으로 id 검색
    @Transactional(readOnly = true)
    public List<MemberDTO> findAllByTeamId(Long teamId) {
        //teamid로 team 찾기
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음"));
        //획득한 team으로 member 찾기
        List<Member> members = memberRepository.findAllByTeam(team);

        return
                members.stream()
                        .map(Member::toDTO)
                        .collect(Collectors.toList());
    }
    // 멤버 검색
    @Transactional(readOnly = true)
    public MemberDTO findById(Long id) {//id로 member 검색

        return findEntityById(id).toDTO();//id로 찾아 DTO로 변환해서 리턴
    }

    //멤버 객체 update
    @Transactional
    public MemberDTO updateById(Long id, MemberDTO dto) {
        Member member = findEntityById(id);
        member.update(dto);

        return memberRepository.saveAndFlush(member).toDTO();
        //id로 멤버를 찾아 dto로 바꿔서 update 후 리턴
    }

    //멤버 객체 delete

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