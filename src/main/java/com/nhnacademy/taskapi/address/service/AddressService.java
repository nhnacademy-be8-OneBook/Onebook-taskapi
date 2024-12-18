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

    public AddMemberAddressResponse addMemberAddress(Long memberId, AddMemberAddressRequest memberAddressRequest){

        MemberAddress memberAddress = new MemberAddress(
                null,
                memberService.getMemberById(memberId),
                memberAddressRequest.getName(),
                memberAddressRequest.getPhoneNumber() ,
                memberAddressRequest.getAlias() ,
                memberAddressRequest.getRequestedTerm() ,
                memberAddressRequest.getZipCode() ,
                memberAddressRequest.getRoadNameAddress(),
                memberAddressRequest.getNumberAddress() ,
                memberAddressRequest.getNotes(),
                memberAddressRequest.getDetailAddress(),
                memberAddressRequest.getDefaultLocation()
        );

        addressRepository.save(memberAddress);

        AddMemberAddressResponse addMemberAddressResponse = new AddMemberAddressResponse(
                memberAddress.getName(),
                memberAddress.getPhoneNumber(),
                memberAddress.getAlias(),
                memberAddress.getRequestedTerm(),
                memberAddress.getZipCode(),
                memberAddress.getRoadNameAddress(),
                memberAddress.getNumberAddress(),
                memberAddress.getNotes(),
                memberAddress.getDetailAddress(),
                memberAddress.getDefaultLocation()
        );

        return addMemberAddressResponse;

    }
    
    public GetMemberAddressResponse getMemberAddress(Long memberId,Long addressId){
         
        MemberAddress memberAddress = addressRepository.findById(addressId)
                .orElseThrow(()-> new MemberAddressNotFoundException("해당하는 ID의 배송지가 존재하지 않습니다"));

        if (memberAddress.getMember().getId() != memberId){
            throw new InvalidMemberAddressException("해당 ID 배송지의 member ID와 요청한 member ID가 일치하지 않습니다.");
        }
        
        GetMemberAddressResponse resp = new GetMemberAddressResponse(
                memberAddress.getName(),
                memberAddress.getPhoneNumber(),
                memberAddress.getAlias(),
                memberAddress.getRequestedTerm(),
                memberAddress.getZipCode(),
                memberAddress.getRoadNameAddress(),
                memberAddress.getNumberAddress(),
                memberAddress.getNotes(),
                memberAddress.getDetailAddress(),
                memberAddress.getDefaultLocation()
        );
        
        return resp;
    }


    public List<GetMemberAddressResponse> getMemberAddresses(Long memberId){

        Member member = memberService.getMemberById(memberId);
        List<GetMemberAddressResponse> resp = new ArrayList<>();

        for(MemberAddress memberAddress :  addressRepository.findMemberAddressByMember(member)){

        GetMemberAddressResponse GetMemberAddressResponse = new GetMemberAddressResponse(
                memberAddress.getName(),
                memberAddress.getPhoneNumber(),
                memberAddress.getAlias(),
                memberAddress.getRequestedTerm(),
                memberAddress.getZipCode(),
                memberAddress.getRoadNameAddress(),
                memberAddress.getNumberAddress(),
                memberAddress.getNotes(),
                memberAddress.getDetailAddress(),
                memberAddress.getDefaultLocation()
        );

        resp.add(GetMemberAddressResponse);

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

        memberAddress.setName(updateMemberAddressRequest.getName());
        memberAddress.setPhoneNumber(updateMemberAddressRequest.getPhoneNumber());
        memberAddress.setAlias(updateMemberAddressRequest.getAlias());
        memberAddress.setRequestedTerm(updateMemberAddressRequest.getRequestedTerm());
        memberAddress.setZipCode(updateMemberAddressRequest.getZipCode());
        memberAddress.setRoadNameAddress(updateMemberAddressRequest.getRoadNameAddress());
        memberAddress.setNumberAddress(updateMemberAddressRequest.getNumberAddress());
        memberAddress.setNotes(updateMemberAddressRequest.getNotes());
        memberAddress.setDetailAddress(updateMemberAddressRequest.getDetailAddress());
        memberAddress.setDefaultLocation(updateMemberAddressRequest.getDefaultLocation());

        UpdateMemberAddressResponse resp = new UpdateMemberAddressResponse(
                memberAddress.getId(),
                memberAddress.getName(),
                memberAddress.getPhoneNumber(),
                memberAddress.getAlias(),
                memberAddress.getRequestedTerm(),
                memberAddress.getZipCode(),
                memberAddress.getRoadNameAddress(),
                memberAddress.getNumberAddress(),
                memberAddress.getNotes(),
                memberAddress.getDetailAddress(),
                memberAddress.getDefaultLocation()
        );

        return resp;

    }


    public DeleteMemberAddressResponse deleteMemberAddress(Long memberId , DeleteMemberAddressRequest deleteMemberAddressRequest){

        MemberAddress memberAddress =  addressRepository.findById(deleteMemberAddressRequest.getId())
                .orElseThrow(()->new MemberAddressNotFoundException("해당하는 ID의 배송지가 존재하지 않습니다"));

        if (memberAddress.getMember().getId() != memberId){
            throw new InvalidMemberAddressException("해당 ID 배송지의 member ID와 요청한 member ID가 일치하지 않습니다.");
        }

        addressRepository.delete(memberAddress);
        DeleteMemberAddressResponse resp = new DeleteMemberAddressResponse(memberId);
        return resp;

    }

}
