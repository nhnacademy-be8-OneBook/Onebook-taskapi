package com.nhnacademy.taskapi.member.controller;

import com.nhnacademy.taskapi.grade.domain.Grade;
import com.nhnacademy.taskapi.grade.service.GradeService;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.dto.MemberResponseDto;
import com.nhnacademy.taskapi.member.service.MemberService;
import com.nhnacademy.taskapi.roles.domain.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private GradeService gradeService;

//    @Test
//    @DisplayName("GET All Members")
//    void getMembersTest() throws Exception {
//        Grade grade = Grade.create("ROYAL", 10, "일반 등급");
//        Role role = Role.createRole("MEMBER", "일반 회원");
//        Member member = Member.createNewMember(grade, "김주혁", "joo", "jjjjjjjjjj", LocalDate.now(), Member.Gender.M, "helloworld@gmail.com", "01011111111", role);
//
//        List<Member> memberList = List.of(member);
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<Member> memberPage = new PageImpl<>(memberList, pageable, memberList.size());
//        Page<MemberResponse> result = memberPage.map(MemberResponse::from);
//
//        Mockito.when(memberService.getAllMembers(pageable)).thenReturn(result);
//
//        mockMvc.perform(get("/task/admin/members/list?page=0&size=10"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.totalPages").value(1))
//                .andExpect(jsonPath("$.size").value(pageable.getPageSize()))
//                .andExpect(jsonPath("$.content[0].name").value(member.getName()))
//                .andExpect(jsonPath("$.content[0].loginId").value(member.getLoginId()))
//                .andExpect(jsonPath("$.content[0].gender").value("M"))
//                .andExpect(jsonPath("$.content[0].email").value(member.getEmail()))
//                .andExpect(jsonPath("$.content[0].phoneNumber").value(member.getPhoneNumber()))
//                .andExpect(jsonPath("$.content[0].status").value("ACTIVE"));
//    }

    @Test
    @DisplayName("GET Member by memberId")
    void getMemberByIdTest() throws Exception {
        Grade grade = Grade.create("ROYAL", 10, "일반 등급");
        Role role = Role.createRole("MEMBER", "일반 회원");
        Member member = Member.createNewMember(grade, "김주혁", "joo", "jjjjjjjjjj", LocalDate.now(), Member.Gender.M, "helloworld@gmail.com", "01011111111", role);
        MemberResponseDto result = MemberResponseDto.from(member);

        Mockito.when(memberService.getMemberById(Mockito.anyLong())).thenReturn(result);

        mockMvc.perform(get("/task/members")
                        .header("X-MEMBER-ID", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("김주혁"))
                .andExpect(jsonPath("$.loginId").value("joo"))
                .andExpect(jsonPath("$.gender").value("M"))
                .andExpect(jsonPath("$.email").value("helloworld@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("01011111111"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    @DisplayName("POST Member")
    void createMemberTest() throws Exception {
        Grade grade = Grade.create("ROYAL", 10, "일반 등급");
        Role role = Role.createRole("MEMBER", "일반 회원");
        Member member = Member.createNewMember(grade, "김주혁", "joo", "jjjjjjjjjj", LocalDate.now(), Member.Gender.M, "helloworld@gmail.com", "01011111111", role);
        MemberResponseDto result = MemberResponseDto.from(member);

        Mockito.when(memberService.registerMember(Mockito.any())).thenReturn(result);

        mockMvc.perform(post("/task/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"김주혁\",\n" +
                                "  \"loginId\": \"joo\",\n" +
                                "  \"password\": \"jjjjjjjjjj\",\n" +
                                "  \"dateOfBirth\": \"2024-12-20\",\n" +
                                "  \"gender\": \"M\",\n" +
                                "  \"email\": \"helloworld@gmail.com\",\n" +
                                "  \"phoneNumber\": \"01011111111\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("김주혁"))
                .andExpect(jsonPath("$.loginId").value("joo"))
                .andExpect(jsonPath("$.gender").value("M"))
                .andExpect(jsonPath("$.email").value("helloworld@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("01011111111"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    @DisplayName("PUT Member")
    void updateMemberTest() throws Exception {
        Grade grade = Grade.create("ROYAL", 10, "일반 등급");
        Role role = Role.createRole("MEMBER", "일반 회원");
        Member member = Member.createNewMember(grade, "김주혁", "joo", "jjjjjjjjjj", LocalDate.now(), Member.Gender.M, "helloworld@gmail.com", "01011111111", role);
        MemberResponseDto result = MemberResponseDto.from(member);

        Mockito.when(memberService.modifyMember(Mockito.any(), Mockito.any())).thenReturn(result);

        mockMvc.perform(put("/task/members")
                        .header("X-MEMBER-ID", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "  \"name\": \"김주혁\",\n" +
                                "  \"password\": \"jjjjjjjjjj\",\n" +
                                "  \"email\": \"helloworld@gmail.com\",\n" +
                                "  \"phoneNumber\": \"01011111111\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("김주혁"))
                .andExpect(jsonPath("$.loginId").value("joo"))
                .andExpect(jsonPath("$.gender").value("M"))
                .andExpect(jsonPath("$.email").value("helloworld@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("01011111111"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    @DisplayName("DELETE Member")
    void deleteMemberTest() throws Exception {
        mockMvc.perform(delete("/task/members")
                .header("X-MEMBER-ID", "1"))
                .andExpect(status().is2xxSuccessful());
    }

    // TODO JWT용 멤버 정보 리턴은 픽스되면 작성.

    @Test
    @DisplayName("GET loginId by memberId")
    void getMemberIdByLoginIdTest() throws Exception {
        Mockito.when(memberService.getLoginIdById(Mockito.anyLong())).thenReturn("joo");

        mockMvc.perform(get("/task/members/loginId")
                        .header("X-MEMBER-ID", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("joo"));
    }

}
