package com.sesac.climb_mates.service

import com.sesac.climb_mates.data.account.Account
import com.sesac.climb_mates.data.account.AccountRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun getAccountByUsername(username:String): Optional<Account> {
        return accountRepository.findByUsername(username)
    }
    fun getAccountById(id:Long):Optional<Account>{
        return accountRepository.findById(id)
    }
    fun createAccount(account: Account): Account {
        account.password = passwordEncoder.encode(account.password)
        account.role = "USER"

        return accountRepository.save(account)
    }
    fun checkUsernameDup(username:String): Boolean {
        return accountRepository.findByUsername(username).isEmpty //true -> 생성 가능
    }
    fun checkEmailDup(email:String): Boolean {
        return accountRepository.findByEmail(email).isEmpty //true -> 생성 가능
    }
    fun checkNicknameDup(nickname:String): Boolean {
        return accountRepository.findByNickname(nickname).isEmpty //true -> 생성 가능
    }
}