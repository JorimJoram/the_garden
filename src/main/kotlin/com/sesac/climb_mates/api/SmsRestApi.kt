package com.sesac.climb_mates.api

import com.sesac.climb_mates.service.SmsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sms/api")
class SmsRestApi(
    private val smsService: SmsService
) {
    @GetMapping("/send")
    fun sendMail(@RequestParam(name="mail", defaultValue = "none")mail:String): String {
        return smsService.validateEmail(mail)
    }

}