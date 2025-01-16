package com.nhnacademy.taskapi.address.controller;

import com.nhnacademy.taskapi.address.domain.dto.req.AddMemberAddressRequest;
import com.nhnacademy.taskapi.address.domain.dto.req.DeleteMemberAddressRequest;
import com.nhnacademy.taskapi.address.domain.dto.req.UpdateMemberAddressRequest;
import com.nhnacademy.taskapi.address.domain.dto.resp.MemberAddressResponse;
import com.nhnacademy.taskapi.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/addresses")
    public ResponseEntity<MemberAddressResponse> addMemberAddress(@RequestHeader("X-MEMBER-ID") Long memberId ,
                                                                  @RequestBody AddMemberAddressRequest request){

        MemberAddressResponse resp = addressService.addMemberAddress(memberId, request);
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<MemberAddressResponse>> getAllMemberAddressByMemberId(@RequestHeader("X-MEMBER-ID") Long memberId){

        List<MemberAddressResponse> resp = addressService.getMemberAddresses(memberId);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<MemberAddressResponse> getMemberAddressById(@RequestHeader("X-MEMBER-ID") Long memberId, @PathVariable Long addressId ){

        MemberAddressResponse resp = addressService.getMemberAddress(memberId,addressId);
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }
    
    @PutMapping("/addresses")
    public ResponseEntity<MemberAddressResponse> updateMemberAddress(@RequestHeader("X-MEMBER-ID") Long memberId, @RequestBody UpdateMemberAddressRequest request){

        MemberAddressResponse resp = addressService.updateMemberAddress(memberId, request);
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

    @DeleteMapping("/addresses")
    public ResponseEntity<MemberAddressResponse> deleteMemberAddress(@RequestHeader("X-MEMBER-ID") Long memberId, @RequestBody DeleteMemberAddressRequest request){

        MemberAddressResponse resp = addressService.deleteMemberAddress(memberId,request);
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

    @GetMapping("/addresses/count")
    public ResponseEntity<Long> getAddressesCount(@RequestHeader("X-MEMBER-ID") Long memberId){

        Long addressCount = addressService.getMemberAddressesCount(memberId);
        return ResponseEntity.ok(addressCount);
    }


}
