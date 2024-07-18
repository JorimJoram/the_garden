package com.sesac.climb_mates.api

import com.sesac.climb_mates.data.account.Account
import com.sesac.climb_mates.service.AccountService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/account/api")
class AccountRestApi(
    private val accountService: AccountService
) {
    @GetMapping("/username-dup")
    fun checkUsernameDup(@RequestParam(defaultValue = "none", name = "username")username:String): Boolean {
        return accountService.checkUsernameDup(username)
    }

    @PostMapping("/create")
    fun userCreate(@RequestBody account:Account){
        println(account);
    }
}