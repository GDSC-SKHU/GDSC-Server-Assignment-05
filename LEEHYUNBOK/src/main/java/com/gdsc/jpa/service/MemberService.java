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

    @Transactional
    public MemberDTO saveByTeamId(Long teamId, MemberDTO dto){

        Team team = findEntityByTeamId(teamId);

        Member member = Member.builder()
                .age(dto.getAge())
                .name(dto.getName())
                .team(team)
                .build();

        return memberRepository.save(member).toDTO();
    }

    @Transactional(readOnly = true)
    public List<MemberDTO> findAll(){
        List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(Member::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MemberDTO> findAllByTeamId(Long teamId){
        Team team = findEntityByTeamId(teamId);

        List<Member> members = memberRepository.findAllByTeam(team);

        return members.stream()
                .map(Member::toDTO)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public MemberDTO findById(Long id){
        Member member = findEntityById(id);

        return member.toDTO();
    }

    @Transactional
    public MemberDTO updateById(Long id, MemberDTO dto){
        Member member = findEntityById(id);

        member.update(dto);

        memberRepository.saveAndFlush(member);

        return member.toDTO();
    }

    @Transactional
    public void deleteById(Long id){
        Member member = findEntityById(id);

        memberRepository.delete(member);
    }

    Member findEntityById(Long id){
        return memberRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 ID의 member은 존재하지 않습니다."));
    }

    Team findEntityByTeamId(Long teamId){
        return teamRepository.findById(teamId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 팀은 찾을 수 없습니다."));

    }

}
