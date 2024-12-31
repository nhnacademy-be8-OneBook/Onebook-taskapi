package com.nhnacademy.taskapi.address.service;

import com.nhnacademy.taskapi.address.domain.dto.req.AddMemberAddressRequest;
import com.nhnacademy.taskapi.address.domain.dto.req.DeleteMemberAddressRequest;
import com.nhnacademy.taskapi.address.domain.dto.req.UpdateMemberAddressRequest;
import com.nhnacademy.taskapi.address.domain.dto.resp.AddMemberAddressResponse;
import com.nhnacademy.taskapi.address.domain.dto.resp.DeleteMemberAddressResponse;
import com.nhnacademy.taskapi.address.domain.dto.resp.GetMemberAddressResponse;
import com.nhnacademy.taskapi.address.domain.dto.resp.UpdateMemberAddressResponse;
import com.nhnacademy.taskapi.address.domain.entity.MemberAddress;
import com.nhnacademy.taskapi.address.exception.InvalidMemberAddressException;
import com.nhnacademy.taskapi.address.exception.MemberAddressNotFoundException;
import com.nhnacademy.taskapi.address.repository.AddressRepository;
import com.nhnacademy.taskapi.member.domain.Member;
import com.nhnacademy.taskapi.member.exception.MemberNotFoundException;
import com.nhnacademy.taskapi.member.repository.MemberRepository;
import com.nhnacademy.taskapi.member.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final MemberService memberService;
    /**
     * 수정일: 2024/12/31
     * 수정자: 김주혁
     * 수정 내용: memberRepository 추가.
     */
    private final MemberRepository memberRepository;

    public AddMemberAddressResponse addMemberAddress(Long memberId, AddMemberAddressRequest memberAddressRequest){
        // memberId null 체크, memberAddressRequest null 체크는 컨트롤러에서

        /**
         * 수정일: 2024/12/31
         * 수정자: 김주혁
         * 수정 내용: 기존 내용 주석처리, memberService.getMemberById() -> memberRepository.findById()
         */
        Member member = memberRepository.findById(memberId).orElseThrow(()-> new MemberNotFoundException("Member Not Found by " + memberId));
//        Member member = memberService.getMemberById(memberId);
        MemberAddress memberAddress = MemberAddress.createMemberAddress(member, memberAddressRequest);
        addressRepository.save(memberAddress);

        return AddMemberAddressResponse.changeEntityToDto(memberAddress);
    }
    
    public GetMemberAddressResponse getMemberAddress(Long memberId,Long addressId){
         
        MemberAddress memberAddress = addressRepository.findById(addressId)
                .orElseThrow(()-> new MemberAddressNotFoundException("해당하는 ID의 배송지가 존재하지 않습니다"));

        if (memberAddress.getMember().getId() != memberId){
            throw new InvalidMemberAddressException("해당 ID 배송지의 member ID와 요청한 member ID가 일치하지 않습니다.");
        }
        
        return GetMemberAddressResponse.changeEntityToDto(memberAddress);
    }


    public List<GetMemberAddressResponse> getMemberAddresses(Long memberId){

        /**
         * 수정일: 2024/12/31
         * 수정자: 김주혁
         * 수정 내용: 기존 내용 주석 처리, memberService.getMemberById() -> memberRepository.findById()
         */
        Member member = memberRepository.findById(memberId).orElseThrow(()-> new MemberNotFoundException("Member Not Found by " + memberId));
//        Member member = memberService.getMemberById(memberId);
        List<GetMemberAddressResponse> resp = new ArrayList<>();

        for(MemberAddress memberAddress :  addressRepository.findMemberAddressByMember(member)){
            resp.add(GetMemberAddressResponse.changeEntityToDto(memberAddress));
        }

        return resp;
    }

    @Transactional
    public UpdateMemberAddressResponse updateMemberAddress(Long memberId, UpdateMemberAddressRequest updateMemberAddressRequest){

        MemberAddress memberAddress = addressRepository.findById(updateMemberAddressRequest.getId())
                .orElseThrow(()-> new MemberAddressNotFoundException("해당하는 ID의 배송지가 존재하지 않습니다"));

        if (memberAddress.getMember().getId() != memberId){
            throw new InvalidMemberAddressException("해당 ID 배송지의 member ID와 요청한 member ID가 일치하지 않습니다.");
        }

        memberAddress.updateMemberAddress(updateMemberAddressRequest);

        return UpdateMemberAddressResponse.changeEntityToDto(memberAddress);
    }


    public DeleteMemberAddressResponse deleteMemberAddress(Long memberId , DeleteMemberAddressRequest deleteMemberAddressRequest){

        MemberAddress memberAddress =  addressRepository.findById(deleteMemberAddressRequest.getId())
                .orElseThrow(()->new MemberAddressNotFoundException("해당하는 ID의 배송지가 존재하지 않습니다"));

        if (memberAddress.getMember().getId() != memberId){
            throw new InvalidMemberAddressException("해당 ID 배송지의 member ID와 요청한 member ID가 일치하지 않습니다.");
        }

        addressRepository.delete(memberAddress);
        return DeleteMemberAddressResponse.changeEntityToDto(memberAddress.getId());

    }

}
