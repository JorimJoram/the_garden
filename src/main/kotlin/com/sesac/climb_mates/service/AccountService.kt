package com.sesac.climb_mates.service

import com.sesac.climb_mates.data.account.Account
import com.sesac.climb_mates.data.account.AccountRepository
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
    private val httpSession: HttpSession
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

    fun updateProfileImage(username: String, img: MultipartFile): Boolean {
        val account = accountRepository.findByUsername(username).get()
        val projectPath = System.getProperty("user.dir") + "/src/main/resources/static/img/profiles"
        val uuid = UUID.randomUUID()

        val fileName = "${uuid}_${img.originalFilename}"
        val saveFile = File(projectPath, fileName)
        img.transferTo(saveFile)

        account.imagePath = ("/img/profiles/${fileName}")

        if(accountRepository.save(account).id!! <= 0)
            return false

        httpSession.setAttribute("session_profile", account.imagePath)
        return true
    }

    fun updateNickname(username: String, nickname: String): Boolean {
        val account = accountRepository.findByUsername(username).get()
        account.nickname = nickname
        if(accountRepository.save(account).id!! <= 0)
            return false

        return true
    }
}