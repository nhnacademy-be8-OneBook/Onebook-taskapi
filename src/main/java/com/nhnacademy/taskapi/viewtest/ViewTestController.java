package com.nhnacademy.taskapi.viewtest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewTestController {

    @GetMapping("/test/404")
    public String get404(){
        return "404";
    }

    @GetMapping("/test/about-us")
    public String getAboutUs(){
        return "about-us";
    }

    @GetMapping("/test/blog-grid")
    public String getBlogGrid(){
        return "blog-grid";
    }

    @GetMapping("/test/blog-grid-sidebar")
    public String getBlogGridSidebar(){
        return "blog-grid-sidebar";
    }

    @GetMapping("/test/blog-single")
    public String getBlogSingle(){
        return "blog-single";
    }

    @GetMapping("/test/blog-single-sidebar")
    public String getBlogSingleSidebar(){
        return "blog-single-sidebar";
    }

    @GetMapping("/test/cart")
    public String getCart(){
        return "cart";
    }

    @GetMapping("/test/checkout")
    public String getCheckout(){
        return "checkout";
    }

    @GetMapping("/test/contact")
    public String getContact(){
        return "contact";
    }

    @GetMapping("/test/index")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/test/index2")
    public String getIndex2(){
        return "index2";
    }

    @GetMapping("/test/index3")
    public String getIndex3(){
        return "index3";
    }

    @GetMapping("/test/index4")
    public String getIndex4(){
        return "index4";
    }

    @GetMapping("/test/login")
    public String getLogin(){
        return "login";
    }

    @GetMapping("/test/mail-success")
    public String getMailSuccess(){
        return "mail-success";
    }

    @GetMapping("/test/register")
    public String getRegister(){
        return "register";
    }

    @GetMapping("/test/shop-grid")
    public String getShopGrid(){
        return "shop-grid";
    }

    @GetMapping("/test/shop-list")
    public String getShopList(){
        return "shop-list";
    }

    @GetMapping("/test/shop-single")
    public String getShopSingle(){
        return "shop-single";
    }

}
