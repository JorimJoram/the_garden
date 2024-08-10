package com.sesac.climb_mates.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/test")
class TestController {
    @GetMapping("/")
    fun testPage(): String {
        return "test/main"
    }
}