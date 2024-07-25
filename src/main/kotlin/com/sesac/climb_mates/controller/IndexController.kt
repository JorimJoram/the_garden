package com.sesac.climb_mates.controller

import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController(

) {
    @GetMapping("/")
    fun indexPage():String{
        return "main/main"
    }
    @GetMapping("/login")
    fun loginPage():String{
        return "account/login"
    }
    @GetMapping("/test")
    fun testPage(): String {
        return "test";
    }
}