package com.sesac.climb_mates.controller

import com.sesac.climb_mates.data.account.Account
import com.sesac.climb_mates.service.AccountService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MypageController(
    private val accountService: AccountService
) {
    @GetMapping("/mypage/main")
    fun myPage(@AuthenticationPrincipal user: User, model: Model): String {
        val userData = accountService.getAccountByUsername(user.username).orElseGet {
            Account(id=-1L,username="", password="", nickname = "", email = "", birth = "", gender = -1, campus = "", education = "", name="")
        }
        model.addAttribute("userData", userData)

        return "account/mypage";
    }
}