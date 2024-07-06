package com.sesac.climb_mates.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController {
    @GetMapping("/")
    fun indexPage():String{
        return "main/main"
    }
}