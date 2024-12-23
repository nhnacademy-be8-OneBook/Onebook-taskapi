package com.nhnacademy.taskapi.member.service;

import com.nhnacademy.taskapi.grade.domain.Grade;
import com.nhnacademy.taskapi.grade.service.impl.GradeServiceImpl;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.dto.MemberModifyDto;
import com.nhnacademy.taskapi.member.dto.MemberRegisterDto;
import com.nhnacademy.taskapi.member.exception.MemberAlreadyExistsException;
import com.nhnacademy.taskapi.member.exception.MemberDataIntegrityViolationException;
import com.nhnacademy.taskapi.member.exception.MemberIllegalArgumentException;
import com.nhnacademy.taskapi.member.exception.MemberNotFoundException;
import com.nhnacademy.taskapi.member.repository.MemberRepository;
import com.nhnacademy.taskapi.member.service.impl.MemberServiceImpl;
import com.nhnacademy.taskapi.roles.domain.Role;
import com.nhnacademy.taskapi.roles.service.impl.RoleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private GradeServiceImpl gradeService;

    @Mock
    private RoleServiceImpl roleService;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberService;

    // TODO 쿠폰, 포인트 완성 시 회원가입 수정 필요.

    @Test
    @DisplayName("Get All Members Successfully")
    void getAllMembersTest() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("createdAt")));
        Mockito.when(memberRepository.findAll(pageable)).thenReturn(Mockito.any());

        memberService.getAllMembers(pageable.getPageNumber());

        Mockito.verify(memberRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Get All Members Failed - Negative page number")
    void getAllMembersFailedTest() {
        assertThrows(MemberIllegalArgumentException.class, () -> memberService.getAllMembers(-1));
    }

    @Test
    @DisplayName("Get Member Successfully")
    void getMemberByIdTest() {
        Grade grade = Grade.create("ROYAL", 10, "일반 등급");
        Role role = Role.createRole("MEMBER", "일반 회원");
        Member member = Member.createNewMember(grade, "김주혁", "joo", "jjjjjjjjjj", LocalDate.now(), Member.Gender.M, "helloworld@gmail.com", "010-1111-1111", role);

        Mockito.when(memberRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(memberRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(member));

        memberService.getMemberById(1L);

        Mockito.verify(memberRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Get Member Failed 1 - memberId doesn't exist")
    void getMemberByIdFailedTest1() {
        assertThrows(MemberIllegalArgumentException.class, () -> memberService.getMemberById(-1L));
    }

    @Test
    @DisplayName("Get Member Failed 2 - DB error")
    void getMemberByIdFailedTest2() {
        Mockito.when(memberRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(memberRepository.findById(Mockito.anyLong())).thenThrow(MemberNotFoundException.class);

        assertThrows(MemberNotFoundException.class, () -> memberService.getMemberById(1l));
    }

    @Test
    @DisplayName("Get Member by LoginId Successfully")
    void getMemberByLoginIdTest() {
        Grade grade = Grade.create("ROYAL", 10, "일반 등급");
        Role role = Role.createRole("MEMBER", "일반 회원");
        Member member = Member.createNewMember(grade, "김주혁", "joo", "jjjjjjjjjj", LocalDate.now(), Member.Gender.M, "helloworld@gmail.com", "010-1111-1111", role);

        Mockito.when(memberRepository.findByLoginId(Mockito.anyString())).thenReturn(Optional.of(member));

        memberService.getMemberByLoginId("joo");

        Mockito.verify(memberRepository, Mockito.times(1)).findByLoginId(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Member by LoginId Failed 1 - memberId doens't exist")
    void getLoginByIdFailedTest1() {
        Mockito.when(memberRepository.existsById(Mockito.anyLong())).thenReturn(false);

        assertThrows(MemberIllegalArgumentException.class, () -> memberService.getLoginIdById(1L));

    }

    @Test
    @DisplayName("Get Member by LoginId Failed 2 - member not found")
    void getLoginByIdFailedTest2() {
        Mockito.when(memberRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(memberRepository.getLoginIdById(Mockito.anyLong())).thenThrow(MemberNotFoundException.class);

        assertThrows(MemberNotFoundException.class, () -> memberService.getLoginIdById(1L));

    }


    @Test
    @DisplayName("Exists Id Successfully")
    void existsByIdTest() {
        memberService.existsById(1L);

        Mockito.verify(memberRepository, Mockito.times(1)).existsById(Mockito.anyLong());
    }

    @Test
    @DisplayName("Exists LoginId Successfully")
    void existsByLoginIdTest() {
        memberService.existsByLoginId("joo");

        Mockito.verify(memberRepository, Mockito.times(1)).existsByLoginId(Mockito.anyString());
    }

    @Test
    @DisplayName("Register Member Successfully")
    void registerMemberTest() {
        Grade grade = Grade.create("ROYAL", 10, "일반 등급");
        Role role = Role.createRole("MEMBER", "일반 회원");

        Mockito.when(memberRepository.existsByLoginId(Mockito.anyString())).thenReturn(false);
        Mockito.when(gradeService.getDefaultGrade()).thenReturn(grade);
        Mockito.when(roleService.getRoleById(Mockito.anyInt())).thenReturn(role);

        memberService.registerMember(new MemberRegisterDto(
                "김주혁",
                "joo",
                "jjjjjjjjjj",
                LocalDate.now(),
                Member.Gender.M,
                "helloworld@gmail.com",
                "010-1111-1111"
        ));

        Mockito.verify(memberRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Register Member Failed 1 - loginId already exists")
    void registerMemberFailedTest1() {
        Mockito.when(memberService.existsByLoginId(Mockito.anyString())).thenReturn(true);

        assertThrows(MemberAlreadyExistsException.class,
                () -> memberService.registerMember(new MemberRegisterDto(
                "김주혁",
                "joo",
                "jjjjjjjjjj",
                LocalDate.now(),
                Member.Gender.M,
                "helloworld@gmail.com",
                "010-1111-1111"
        )));

    }

    @Test
    @DisplayName("Register Member Failed 2 - DB error")
    void registerMemberFailedTest2() {
        Grade grade = Grade.create("ROYAL", 10, "일반 등급");
        Role role = Role.createRole("MEMBER", "일반 회원");

        Mockito.when(memberRepository.existsByLoginId(Mockito.anyString())).thenReturn(false);
        Mockito.when(gradeService.getDefaultGrade()).thenReturn(grade);
        Mockito.when(roleService.getRoleById(Mockito.anyInt())).thenReturn(role);
        Mockito.when(memberRepository.save(Mockito.any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(MemberDataIntegrityViolationException.class,

                () -> memberService.registerMember(new MemberRegisterDto(
                "김주혁",
                "joo",
                "jjjjjjjjjj",
                LocalDate.now(),
                Member.Gender.M,
                "helloworld@gmail.com",
                "010-1111-1111"
        )));
    }

    @Test
    @DisplayName("Modify Member Successfully 1 - Password changed")
    void modifyMemberTest1() {
        Grade grade = Grade.create("ROYAL", 10, "일반 등급");
        Role role = Role.createRole("MEMBER", "일반 회원");
        Member member = Member.createNewMember(grade, "김주혁", "joo", BCrypt.hashpw("jjjjjjjjjj", BCrypt.gensalt()), LocalDate.now(), Member.Gender.M, "helloworld@gmail.com", "010-1111-1111", role);

        Mockito.when(memberRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(memberRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(member));

        memberService.modifyMember(Mockito.anyLong() ,new MemberModifyDto(
                "혁주김",
                "oooooooooo",
                "worldhello@gmail.com",
                "010-2222-2222"
        ));

        Mockito.verify(memberRepository, Mockito.times(1)).findById(Mockito.any());

    }

    @Test
    @DisplayName("Modify Member Successfully 2 - Password not changed")
    void modifyMemberTest2() {
        Grade grade = Grade.create("ROYAL", 10, "일반 등급");
        Role role = Role.createRole("MEMBER", "일반 회원");
        Member member = Member.createNewMember(grade, "김주혁", "joo", BCrypt.hashpw("jjjjjjjjjj", BCrypt.gensalt()), LocalDate.now(), Member.Gender.M, "helloworld@gmail.com", "010-1111-1111", role);

        Mockito.when(memberRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(memberRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(member));

        memberService.modifyMember(Mockito.anyLong() ,new MemberModifyDto(
                "혁주김",
                "jjjjjjjjjj",
                "worldhello@gmail.com",
                "010-2222-2222"
        ));

        Mockito.verify(memberRepository, Mockito.times(1)).findById(Mockito.any());
    }

//    @Test
//    @DisplayName("Modify Member Failed - Db error")
//    void modifyMemberFailedTest() {
//        Grade grade = Grade.create("ROYAL", 10, "일반 등급");
//        Role role = Role.createRole("MEMBER", "일반 회원");
//        Member member = Member.createNewMember(grade, "김주혁", "joo", BCrypt.hashpw("jjjjjjjjjj", BCrypt.gensalt()), LocalDate.now(), Member.Gender.M, "helloworld@gmail.com", "010-1111-1111", role);
//
//        Mockito.when(memberRepository.existsById(Mockito.anyLong())).thenReturn(true);
//        Mockito.when(memberRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(member));
//        Mockito.when(memberRepository.save(Mockito.any())).thenThrow(DataIntegrityViolationException.class);
//
//        assertThrows(MemberDataIntegrityViolationException.class,
//                () -> memberService.modifyMember(Mockito.anyLong() ,new MemberModifyDto(
//                "혁주김",
//                "jjjjjjjjjj",
//                "worldhello@gmail.com",
//                "010-2222-2222"
//        )));
//    }

    @Test
    @DisplayName("Remove Member Successfully")
    void removeMemberTest() {
        Grade grade = Grade.create("ROYAL", 10, "일반 등급");
        Role role = Role.createRole("MEMBER", "일반 회원");
        Member member = Member.createNewMember(grade, "김주혁", "joo", BCrypt.hashpw("jjjjjjjjjj", BCrypt.gensalt()), LocalDate.now(), Member.Gender.M, "helloworld@gmail.com", "010-1111-1111", role);

        Mockito.when(memberRepository.existsById(Mockito.anyLong())).thenReturn(true);
        Mockito.when(memberRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(member));

        memberService.removeMember(Mockito.anyLong());

        Mockito.verify(memberRepository, Mockito.times(1)).findById(Mockito.any());
    }

//    @Test
//    @DisplayName("Remove Member Failed - DB error")
//    void removeMemberFailedTest() {
//        Grade grade = Grade.create("ROYAL", 10, "일반 등급");
//        Role role = Role.createRole("MEMBER", "일반 회원");
//        Member member = Member.createNewMember(grade, "김주혁", "joo", BCrypt.hashpw("jjjjjjjjjj", BCrypt.gensalt()), LocalDate.now(), Member.Gender.M, "helloworld@gmail.com", "010-1111-1111", role);
//
//        Mockito.when(memberRepository.existsById(Mockito.anyLong())).thenReturn(true);
//        Mockito.when(memberRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(member));
//        Mockito.when(memberRepository.save(Mockito.any())).thenThrow(DataIntegrityViolationException.class);
//
//        assertThrows(MemberDataIntegrityViolationException.class,
//                () -> memberService.removeMember(Mockito.anyLong()));
//    }

    /**
     * 로그인, 로그아웃 테스트 보류.
     */



















}
