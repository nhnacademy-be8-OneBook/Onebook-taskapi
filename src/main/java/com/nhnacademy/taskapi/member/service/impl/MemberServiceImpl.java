package com.nhnacademy.taskapi.member.service.impl;

import com.nhnacademy.taskapi.grade.domain.Grade;
import com.nhnacademy.taskapi.grade.service.GradeService;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.dto.*;
import com.nhnacademy.taskapi.member.exception.MemberIllegalArgumentException;
import com.nhnacademy.taskapi.member.exception.MemberNotFoundException;
import com.nhnacademy.taskapi.member.repository.MemberRepository;
import com.nhnacademy.taskapi.member.service.MemberService;

import com.nhnacademy.taskapi.point.service.PointService;
import com.nhnacademy.taskapi.roles.domain.Role;
import com.nhnacademy.taskapi.roles.service.RoleService;

import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final GradeService gradeService;
    private final RoleService roleService;
    private final PointService pointService;

    // TODO 쿠폰, 포인트 완성 시 회원가입 수정 필요.

    /**
     * 로그인, 로그아웃 기능 보류.
     */

   // 전체 멤버 반환에 Pagenation 적용.
    @Transactional(readOnly = true)
    @Override
    public Page<MemberResponseDto> getAllMembers(Pageable pageable) {
        Page<Member> memberPage = memberRepository.findAll(pageable);
        Page<MemberResponseDto> result = memberPage.map(MemberResponseDto::from);

        return result;
    }

    // 인조키(id)로 멤버 조회
    @Transactional(readOnly = true)
    @Override
    public MemberResponseDto getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member not found by " + id));

        return MemberResponseDto.from(member);
    }

    /**
    * jwt 용 입니다. 멤버 조회하시려면 memberId로 하세요
    * 두레이 메시지에서 jwt에서 memberId 가져오는거 참고하세요
    */
    // 로그인 아이디로 멤버 조회
    @Transactional(readOnly = true)
    @Override
    public Member getMemberByLoginId(String loginId) {
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> new MemberNotFoundException("Member not found by " + loginId));
        return member;
    }

    // memberId로 loginId 조회.
    @Transactional(readOnly = true)
    @Override
    public String getLoginIdById(Long id) {
        return memberRepository.getLoginIdById(id).orElseThrow(() -> new MemberNotFoundException("Member not found by memberId"));
    }


    // 중복 확인 - 회원 인조키(id)가 존재하는지 확인
    @Transactional(readOnly = true)
    @Override
    public boolean existsById(Long id) {
        return memberRepository.existsById(id);
    }

    // 중복 확인 - 회원 로그인 ID(loginId)가 존재하는지 확인
    @Transactional(readOnly = true)
    @Override
    public boolean existsByLoginId(String loginId) {
        return memberRepository.existsByLoginId(loginId);
    }

    // 회원 정보 저장 - 회원 가입
    @Override
    public MemberResponseDto registerMember(MemberRegisterRequestDto memberRegisterRequestDto) {
        if(memberRepository.existsByLoginId(memberRegisterRequestDto.loginId())) {
            throw new MemberIllegalArgumentException("Already exists loginID");
        }

        // password 해시 암호화
        String HashedPassword = BCrypt.hashpw(memberRegisterRequestDto.password(), BCrypt.gensalt());

        Member member = Member.createNewMember(
                Grade.from(gradeService.getDefaultGrade()),
                memberRegisterRequestDto.name(),
                memberRegisterRequestDto.loginId(),
                HashedPassword,
                memberRegisterRequestDto.dateOfBirth(),
                memberRegisterRequestDto.gender(),
                memberRegisterRequestDto.email(),
                memberRegisterRequestDto.phoneNumber(),
                Role.from(roleService.getDefaultRole())
        );

        try {

            Member result = memberRepository.save(member);
            pointService.registerMemberPoints(result);
            // TODO 쿠폰 CRUD 완료하면 Welcome 쿠폰 생성 추가
            return MemberResponseDto.from(result);

        }catch(DataIntegrityViolationException e) {
            throw new MemberIllegalArgumentException("Failed to save member in the database: Invalid format");
        }

    }

    // 회원 정보 수정
    @Override
    public MemberResponseDto modifyMember(Long memberId, MemberModifyRequestDto memberModifyRequestDto) {
       Member member = memberRepository.findById(memberId).orElseThrow(()-> new MemberNotFoundException("Not Found Member by " + memberId));

       // 비밀번호 암호화
       if(BCrypt.checkpw(memberModifyRequestDto.password(), member.getPassword())) { // 비교했는데 같으면 그냥 member password 넣음.
           member.modifyMember(
                   memberModifyRequestDto.name(),
                   member.getPassword(),
                   memberModifyRequestDto.email(),
                   memberModifyRequestDto.phoneNumber()
           );
       } else {
           String HashedPassword = BCrypt.hashpw(memberModifyRequestDto.password(), BCrypt.gensalt()); // 비교했는데 다르면 dto 것을 hash 암호화해서 넣음.
           member.modifyMember(
                   memberModifyRequestDto.name(),
                   HashedPassword,
                   memberModifyRequestDto.email(),
                   memberModifyRequestDto.phoneNumber()
           );
       }

       return MemberResponseDto.from(member);
    }

    // 회원 탈퇴 - 상태만 'DELETED' 로 변경.
    @Override
    public void removeMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(()-> new MemberIllegalArgumentException("Member Not Found by " + memberId));
        member.setStatus(Member.Status.DELETED);
    }

    // 회원 상태 변경
    @Override
    public void changeStatusToActivation(Long memberId, String status) {
        Member member = memberRepository.findById(memberId).orElseThrow(()-> new MemberNotFoundException("Member Not Found by " + memberId));

        switch(status) {
            case "ACTIVE":
                member.setStatus(Member.Status.ACTIVE);
                break;
            case "SUSPENDED":
                member.setStatus(Member.Status.SUSPENDED);
                break;
        }
    }

    // 멤버 로그인 기록 업데이트
    @Override
    public void updateMemberLoginId(String loginId) {
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(()-> new MemberNotFoundException("Member Not Found by " + loginId));
        member.setLastLoginAt(LocalDateTime.now());
    }

    // 회원 여부 조회
    @Override
    public MembershipCheckResponseDto validateMembership(Long memberId, MembershipCheckRequestDto membershipCheckRequestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(()-> new MemberNotFoundException("Member Not Found by " + memberId));

        // 비밀번호가 일치하면
        if(BCrypt.checkpw(membershipCheckRequestDto.password(), member.getPassword())) {
            return new MembershipCheckResponseDto(true);
        }

        return new MembershipCheckResponseDto(false);
    }



    // 로그인
    @Override
    public MemberLoginDto validateLogin(MemberLoginDto memberLoginDto) {

        // TODO 테스트용 삭제 수정자 문영호
//        return new MemberLoginDto("hihi", "hoho");

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

        // 휴면일 경우 인증해야 하므로 인증 후 front에서 다시 상태 변경 요청을 하도록 함.
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

