package com.nhnacademy.taskapi.address;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddressController {

    @GetMapping("/address/register-form")
    public String getAddressRegisterForm(){
        return "/address/address-register-form";
    }

}
