package com.sesac.climb_mates.config.auth

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.DefaultRedirectStrategy
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component

@Component
class CustomFailureHandler:AuthenticationFailureHandler {
    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        val userAgent = request?.getHeader("User-Agent")
        if(userAgent?.contains("okhttp") == true){
            response?.contentType="application/json"
            response?.characterEncoding = "utf8"
            response?.writer?.write(ObjectMapper().writeValueAsString(failureMsg(exception)))
        }else{
            val redirectStrategy = DefaultRedirectStrategy()
            when(exception){
                is UsernameNotFoundException, is BadCredentialsException -> redirectStrategy.sendRedirect(request, response, "/login?errorCode=BadInputException")
                is AccountExpiredException -> redirectStrategy.sendRedirect(request, response, "/login?errorCode=ExpiredException")
                else -> redirectStrategy.sendRedirect(request, response, "/login?errorCode=UnknownException")
            }
        }
    }
    private fun failureMsg(exception:AuthenticationException?): HashMap<String, String>{
        val message = HashMap<String, String>()
        when(exception){
            is UsernameNotFoundException, is BadCredentialsException -> message["exception"] = "BadInputException"
            is AccountExpiredException -> message["exception"] = "ExpiredException"
            else -> message["code"] = "UnknownException"
        }
        return message
    }

}