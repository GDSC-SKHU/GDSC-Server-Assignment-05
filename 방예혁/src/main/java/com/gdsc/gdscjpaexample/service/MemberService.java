package com.gdsc.gdscjpaexample.service;

import com.gdsc.gdscjpaexample.dto.MemberDTO;
import com.gdsc.gdscjpaexample.entity.Member;
import com.gdsc.gdscjpaexample.entity.Team;
import com.gdsc.gdscjpaexample.repository.MemberRepository;
import com.gdsc.gdscjpaexample.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/*
   public MemberDTO saveByTeamId(Long teamId, MemberDTO dto)
   -> 매개변수로 받은 teamId로 팀을 찾고, 그 팀에 입력받은 dto 기반으로 create member
   public List<MemberDTO> findAll()
   -> DB에 존재하는 모든 멤버 정보를 리스트에 담아 리턴
   public List<MemberDTO> findAllByTeamId(Long teamId)
   -> 매개변수로 받은 teamId로 팀을 찾고, 그 팀에 소속된 모든 멤버 정보를 리스트에 담아 리턴
   public MemberDTO findById(Long id)
   -> 매개변수로 받은 id와 일치하는 id를 가진 member 리턴
   public MemberDTO updateById(Long id, MemberDTO dto)
   -> 매개변수로 받은 id로 멤버를 찾고 dto 기반으로 update 후 리턴
   public void deleteById(Long id)
   -> 매개변수로 받은 id와 일치하는 id를 가진 member 제거
   Member findEntityById(Long id)
   -> 접근제어자 public 안씀, 실질적인 DB에 접근하여 멤버를 가져오는 메소드이기 때문에,
      다른 쪽에서는 접근할 수 없도록 함
*/

@Service
@RequiredArgsConstructor
public class MemberService {

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;

    // create member
    @Transactional
    public MemberDTO saveByTeamId(Long teamId, MemberDTO dto) {
        // teamId로 team 찾고 없으면 예외, 있으면 팀 저장
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음"));

        Member member = Member.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .team(team)
                .build();

        return memberRepository.save(member).toDTO();
    }

    // DB에 존재하는 멤버 정보를 모두 리스트에 담아 리턴
    @Transactional(readOnly = true)
    public List<MemberDTO> findAll() {
        List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(Member::toDTO)
                .collect(Collectors.toList());
    }

    // DB에 존재하는 팀 중에서 id로 그 팀을 찾고, 그 팀에 존재하는 멤버 정보를 리스트로 담아 리턴
    @Transactional(readOnly = true)
    public List<MemberDTO> findAllByTeamId(Long teamId) {
        // teamId로 team 찾고 없으면 예외, 있으면 팀 저장
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Team 찾을 수 없음"));

        List<Member> members = memberRepository.findAllByTeam(team);

        return members.stream()
                .map(Member::toDTO)
                .collect(Collectors.toList());
    }

    // DB에 존재하는 멤버 중 매개변수로 받은 id와 같은 id를 가진 멤버 리턴
    @Transactional(readOnly = true)
    public MemberDTO findById(Long id) {
        return findEntityById(id).toDTO();
    }

    // DB에 존재하는 멤버 중 매개변수로 받은 id와 같은 id를 가진 멤버 업데이트 후 리턴
    @Transactional
    public MemberDTO updateById(Long id, MemberDTO dto) {
        Member member = findEntityById(id);
        member.update(dto);

        return memberRepository.saveAndFlush(member).toDTO();
    }

    // DB에 존재하는 멤버 중 매개변수로 받은 id와 같은 id를 가진 멤버 삭제
    @Transactional
    public void deleteById(Long id) {
        Member member = findEntityById(id);
        memberRepository.delete(member);
    }

    // 실질적인 DB에 id로 멤버 찾기, 없으면 에러 메시지
    @Transactional(readOnly = true)
    Member findEntityById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID로 Member 찾을 수 없음"));
    }
}
