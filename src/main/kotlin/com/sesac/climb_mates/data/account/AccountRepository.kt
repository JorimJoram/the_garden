package com.sesac.climb_mates.data.account

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import javax.swing.text.html.Option

interface AccountRepository:JpaRepository<Account, Long> {
    fun findByUsername(username:String):Optional<Account>
    fun findByEmail(email:String):Optional<Account>
    fun findByNickname(nickname:String):Optional<Account>
}