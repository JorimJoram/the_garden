package com.sesac.climb_mates.controller

import jakarta.servlet.RequestDispatcher
import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller

class CustomErrorController: ErrorController {
    @RequestMapping("/error")
    fun errorHandle(request:HttpServletRequest): String {
        val status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)

        var errorPath = "/error/"

        if(status != null){
            val statusCode = Integer.parseInt(status.toString())

            if(statusCode == HttpStatus.FORBIDDEN.value())
                errorPath +=  "forbidden.mustache"
            else if(statusCode == HttpStatus.NOT_FOUND.value())
                errorPath += "not_found"
            else
                errorPath += "error"
        }

        return errorPath
    }
}