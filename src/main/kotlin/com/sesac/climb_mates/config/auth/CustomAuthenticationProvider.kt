package com.sesac.climb_mates.config.auth

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationProvider(
    private val passwordEncoder: PasswordEncoder,
    private val customUserDetailService: CustomUserDetailService
): AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication {
        val username = authentication!!.name
        val password = authentication.credentials.toString()
        val storedAccountInfo = customUserDetailService.loadUserByUsername(username)

        if(!passwordEncoder.matches(password, storedAccountInfo.password))
            throw BadCredentialsException("Invalid user Password")

        return UsernamePasswordAuthenticationToken(storedAccountInfo, null, storedAccountInfo.authorities)
        //원래는 비밀번호를 2번째에서 같이 내보내야하는데, 굳이 토큰에 비밀번호를 넘기는 이유를 모르겠어서 + 이미 저장했던 계정 객체 그 자체를 보내는데 왜 필요한지 아예 몰라서
        //gpt도 굳이 적을 필요가 없다하여 null 값으로 두고 넘기겠습니다
        //혹시나 계정 인증하는데 문제 발생하면 여기일 수도 있습니다
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return authentication?.let { UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(it) }!!
    }

}