package com.sesac.climb_mates.config.auth

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CustomSuccessHandler:AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
         val userAgent = request?.getHeader("User-Agent")

        if(userAgent?.contains("okhttp") == true){
            //모바일로 접속했을 때의 경우임 -> okhttp가 retrofit에서 사용하는거라 그러함
            response?.contentType = "applicationType/json"
            val token = SecurityContextHolder.getContext().authentication
            //response?.writer?.write(ObjectMapper().writeValueAsString(token))
            response?.writer?.write(ObjectMapper().writeValueAsString(authentication?.principal as UserDetails))
        }else{
            val redirectStrategy = DefaultRedirectStrategy()
            redirectStrategy.sendRedirect(request, response, "/")
        }
        println("Login: ${request!!.requestId} / ${LocalDateTime.now()}")
    }
}