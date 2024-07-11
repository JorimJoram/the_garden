package com.sesac.climb_mates.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AccountController {
    @GetMapping("/account")
    fun createAccount(model:Model): String {
        return "account/account"
    }
}


