package com.sesac.climb_mates.api

import com.sesac.climb_mates.data.account.Account
import com.sesac.climb_mates.data.campus.CampusClass
import com.sesac.climb_mates.service.AccountService
import com.sesac.climb_mates.service.CampusService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account/api")
class AccountRestApi(
    private val accountService: AccountService,
    private val campusService: CampusService
) {
    @GetMapping("/username-dup")
    fun checkUsernameDup(@RequestParam(defaultValue = "none", name = "username")username:String): Boolean {
        return accountService.checkUsernameDup(username)
    }

    @GetMapping("/email-dup")
    fun checkEmailDup(@RequestParam(defaultValue = "none", name="email")email:String): Boolean {
        return accountService.checkEmailDup(email)
    }

    @GetMapping("/nickname-dup")
    fun checkNicknameDup(@RequestParam(defaultValue = "none", name="nickname")nickname:String): Boolean {
        return accountService.checkNicknameDup(nickname)
    }

    @GetMapping("/campus/list")
    fun getCampusClassList(@RequestParam(defaultValue = "none", name="campus")campus:String): List<CampusClass> {
        return campusService.getCampusClassListByCampus("${campus}캠퍼스")
    }

    @PostMapping("/create")
    fun userCreate(@RequestBody account:Account): Account {
        return accountService.createAccount(account)
    }
}