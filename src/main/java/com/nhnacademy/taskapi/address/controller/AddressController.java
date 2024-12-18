package com.nhnacademy.taskapi.address.controller;

import com.nhnacademy.taskapi.address.domain.dto.req.AddMemberAddressRequest;
import com.nhnacademy.taskapi.address.domain.dto.req.DeleteMemberAddressRequest;
import com.nhnacademy.taskapi.address.domain.dto.req.UpdateMemberAddressRequest;
import com.nhnacademy.taskapi.address.domain.dto.resp.AddMemberAddressResponse;
import com.nhnacademy.taskapi.address.domain.dto.resp.DeleteMemberAddressResponse;
import com.nhnacademy.taskapi.address.domain.dto.resp.GetMemberAddressResponse;
import com.nhnacademy.taskapi.address.domain.dto.resp.UpdateMemberAddressResponse;
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
    public ResponseEntity<AddMemberAddressResponse> addMemberAddress(@RequestHeader("X-MEMBER-ID") Long memberId , @RequestBody AddMemberAddressRequest request){

        AddMemberAddressResponse resp = addressService.addMemberAddress(memberId, request);
        return new ResponseEntity<>(resp,HttpStatus.OK);
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
    public ResponseEntity<UpdateMemberAddressResponse> updateMemberAddress(@RequestHeader("X-MEMBER-ID") Long memberId, @RequestBody UpdateMemberAddressRequest request){

        UpdateMemberAddressResponse resp = addressService.updateMemberAddress(memberId, request);
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

    @DeleteMapping("/addresses")
    public ResponseEntity<DeleteMemberAddressResponse> deleteMemberAddress(@RequestHeader("X-MEMBER-ID") Long memberId, @RequestBody DeleteMemberAddressRequest request){

        DeleteMemberAddressResponse resp = addressService.deleteMemberAddress(memberId,request);
        return new ResponseEntity<>(resp,HttpStatus.OK);
    }

}
