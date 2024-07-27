package com.sesac.climb_mates.api

import com.sesac.climb_mates.service.AccountService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/mypage/api")
class MypageRestApi(
    private val accountService: AccountService
) {
    @GetMapping("/nickname-dup")
    fun checkNicknameDup(@RequestParam(defaultValue = "none", name="nickname")nickname:String): Boolean {
        return accountService.checkNicknameDup(nickname)
    }

    @PutMapping("/nickname")
    fun updateNickname(@AuthenticationPrincipal user: User, @RequestParam(name="nickname") nickname: String): Boolean {
        return accountService.updateNickname(user.username, nickname)
    }

    @PutMapping("/img")
    fun updateProfileImage(@AuthenticationPrincipal user: User, @RequestParam("img")img: MultipartFile): Boolean {
        return accountService.updateProfileImage(user.username, img)
    }
}