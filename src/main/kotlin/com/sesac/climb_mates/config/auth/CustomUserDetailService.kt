package com.sesac.climb_mates.config.auth

import com.sesac.climb_mates.data.account.AccountRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class CustomUserDetailService(
    private val accountRepository: AccountRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val siteUser = username?.let {
            accountRepository.findByUsername(it).orElseThrow{UsernameNotFoundException("User not found")}
        }?: throw UsernameNotFoundException("User not found")

        val authorities = mutableListOf<SimpleGrantedAuthority>()
        when(siteUser.role){
            "USER" -> authorities.add(SimpleGrantedAuthority("USER"))
            "ADMIN" -> authorities.add(SimpleGrantedAuthority("ADMIN"))
            else -> throw RuntimeException("store error")
        }

        return User(siteUser.username, siteUser.password, authorities)
    }
}