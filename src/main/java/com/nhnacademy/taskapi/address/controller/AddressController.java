package com.nhnacademy.taskapi.address.controller;

import com.nhnacademy.taskapi.address.domain.dto.req.AddMemberAddressRequest;
import com.nhnacademy.taskapi.address.domain.dto.req.DeleteMemberAddressRequest;
import com.nhnacademy.taskapi.address.domain.dto.req.UpdateMemberAddressRequest;
import com.nhnacademy.taskapi.address.domain.dto.resp.GetMemberAddressResponse;
import com.nhnacademy.taskapi.address.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/addresses")
    public ResponseEntity addMemberAddress(@RequestHeader("X-MEMBER-ID") Long memberId , @RequestBody AddMemberAddressRequest request){

        addressService.addMemberAddress(memberId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<GetMemberAddressResponse>> getAllMemberAddressByMemberId(@RequestHeader("X-MEMBER-ID") Long memberId){

        List<GetMemberAddressResponse> resp = addressService.getMemberAddresses(memberId);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<GetMemberAddressResponse> getMemberAddressById(@RequestHeader("X-MEMBER-ID") Long memberId, @PathVariable Long addressId ){

        GetMemberAddressResponse resp = addressService.getMemberAddress(memberId,addressId);
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }
    
    @PutMapping("/addresses")
    public ResponseEntity updateMemberAddress(@RequestHeader("X-MEMBER-ID") Long memberId, @RequestBody UpdateMemberAddressRequest request){

        addressService.updateMemberAddress(memberId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/addresses")
    public ResponseEntity deleteMemberAddress(@RequestHeader("X-MEMBER-ID") Long memberId, @RequestBody DeleteMemberAddressRequest request){

        addressService.deleteMemberAddress(memberId,request);
        return ResponseEntity.ok().build();
    }

}
