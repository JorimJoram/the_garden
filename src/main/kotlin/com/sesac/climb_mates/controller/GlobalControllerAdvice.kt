package com.sesac.climb_mates.controller

import jakarta.servlet.http.HttpSession
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

@ControllerAdvice
class GlobalControllerAdvice {
    @ModelAttribute("profileImage")
    fun addProfileImageToModel(httpSession: HttpSession): String {
        val imagePath = httpSession.getAttribute("session_profile") as? String
        println("profileImage:${imagePath}")
        return imagePath ?: "/img/default/profile.png"
    }
}