package com.nhnacademy.taskapi.member.service.impl;

import com.nhnacademy.taskapi.grade.service.GradeService;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.dto.MemberLoginDto;
import com.nhnacademy.taskapi.member.dto.MemberModifyDto;
import com.nhnacademy.taskapi.member.dto.MemberRegisterDto;
import com.nhnacademy.taskapi.member.exception.MemberAlreadyExistsException;
import com.nhnacademy.taskapi.member.exception.MemberDataIntegrityViolationException;
import com.nhnacademy.taskapi.member.exception.MemberIllegalArgumentException;
import com.nhnacademy.taskapi.member.exception.MemberNotFoundException;
import com.nhnacademy.taskapi.member.repository.MemberRepository;
import com.nhnacademy.taskapi.member.service.MemberService;

import com.nhnacademy.taskapi.roles.service.RoleService;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final GradeService gradeService;
    private final RoleService roleService;

    // 전체 멤버 조회
    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // 인조키(id)로 멤버 조회
    @Override
    public Member getMemberById(Long id) {
        if(!existsById(id)) {
            throw new MemberIllegalArgumentException("Member id does not exist");
        }

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member not found by id"));

        return member;
    }

    // 중복 확인 - 회원 인조키(id)가 존재하는지 확인
    @Override
    public boolean existsById(Long id) {
        return memberRepository.existsById(id);
    }

    // 중복 확인 - 회원 로그인 ID(loginId)가 존재하는지 확인
    @Override
    public boolean existsByLoginId(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }

    // 회원 정보 저장 - 회원 가입
    @Override
    public Member registerMember(MemberRegisterDto memberRegisterDto) {
        if(existsByLoginId(memberRegisterDto.loginId())) {
            throw new MemberAlreadyExistsException("Already exists loginID");
        }

        // password 해시 암호화
        String HashedPassword = BCrypt.hashpw(memberRegisterDto.password(), BCrypt.gensalt());

        Member member = Member.createNewMember(
                gradeService.getDefaultGrade(),
                memberRegisterDto.name(),
                memberRegisterDto.loginId(),
                HashedPassword,
                memberRegisterDto.dateOfBirth(),
                memberRegisterDto.gender(),
                memberRegisterDto.email(),
                memberRegisterDto.phoneNumber(),
                roleService.getRoleById(1) // Role Id: 1은 무조건 MEMBER
        );


        try {

            return memberRepository.save(member);

        }catch(DataIntegrityViolationException e) {
            throw new MemberDataIntegrityViolationException("Failed to save member in the database");
        }

    }

    // 회원 정보 수정
    @Override
    public Member modifyMember(Long memberId, MemberModifyDto memberModifyDto) {
       Member member = getMemberById(memberId);
       if(member.getPassword().equals(memberModifyDto.password())) {
           member.modifyMember(
                   memberModifyDto.name(),
                   memberModifyDto.password(),
                   memberModifyDto.email(),
                   memberModifyDto.phoneNumber()
           );
       } else {
           String HashedPassword = BCrypt.hashpw(memberModifyDto.password(), BCrypt.gensalt());
           member.modifyMember(
                   memberModifyDto.name(),
                   HashedPassword,
                   memberModifyDto.email(),
                   memberModifyDto.phoneNumber()
           );
       }

       try {

           return memberRepository.save(member);

       }catch(DataIntegrityViolationException e) {
           throw new MemberDataIntegrityViolationException("Failed to save member in the database");
       }
    }


    // 회원 탈퇴 - 상태만 'DELETED' 로 변경.
    @Override
    public void removeMember(Long memberId) {
        Member member = getMemberById(memberId);
        member.setStatus(Member.Status.DELETED);
    }

    // 로그인
    @Override
    public MemberLoginDto validateLogin(MemberLoginDto memberLoginDto) {
        if(!existsByLoginId(memberLoginDto.loginId())) {
            throw new MemberIllegalArgumentException("Member id does not exist");
        }

        // 로그인 id로 member 정보 가져옴.
        Member member = memberRepository.findByLoginId(memberLoginDto.loginId())
                .orElseThrow(() -> new MemberNotFoundException("Member not found by" + memberLoginDto.loginId()));

        // 회원 상태 검증
        if(member.getStatus().equals(Member.Status.DELETED)) {
            throw new MemberIllegalArgumentException("Member status is DELETED");
        }

        if(member.getStatus().equals(Member.Status.SUSPENDED)) {
            throw new MemberIllegalArgumentException("Member status is SUSPENDED");
        }

//        if(member.getStatus().equals(Member.Status.INACTIVE)) {
//            member.setStatus(Member.Status.ACTIVE);
//        }

        // 최근 로그인 기록 업데이트
        member.setLastLoginAt(LocalDateTime.now());

        if(!BCrypt.checkpw(memberLoginDto.password(), member.getPassword())) {
            throw new MemberIllegalArgumentException("Wrong password");
        }

        return new MemberLoginDto(member.getLoginId(), member.getPassword());
    }

}

