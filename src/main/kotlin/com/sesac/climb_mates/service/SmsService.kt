package com.sesac.climb_mates.service

import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import java.util.stream.IntStream


@Service
class SmsService(
    private val emailSender:JavaMailSender
) {
    private var key:String = ""

    fun validateEmail(email:String): String {
        createKey()
        try{
            sendEmail(email)
        }catch (e:Exception){
            print(e.message)
        }

        return this.key
    }

    fun createMailForm(arrEmail:String): MimeMessage {
        val sender = "lsd4026@naver.com"
        val title = "[SPROUT]계정생성 인증번호 입니다."

        val message = emailSender.createMimeMessage()
        message.addRecipients(MimeMessage.RecipientType.TO, arrEmail)
        message.subject = title
        message.setFrom(sender)
        message.setText(String.format("이메일 인증번호: ${this.key}<br>복사 후 붙여넣기를 통해 인증을 완료해주세요!"), "utf-8", "html")
        //javascript .toUpperCase( )이용하면 모두 대문자로 변형 됩니다

        return message
    }

    fun sendEmail(email:String){
        val emailForm:MimeMessage = createMailForm(email)
        val helper = MimeMessageHelper(emailForm, "utf-8")
        helper.setFrom(InternetAddress("lsd4026@naver.com", "climb-mates"))
        emailSender.send(emailForm)
    }

    private fun createKey() {
        //숫자 0-9, 문자 A-Z
        val numList: IntArray = IntStream.concat(
            IntStream.rangeClosed(65, 90), //A-Z
            IntStream.rangeClosed(48, 57)
        ).toArray()

        val indexList: List<Int> = numList.indices.toList().shuffled()

        //특정 문자가 지나치게 중복되어 이를 해결하고자 전체 요소를 다시 섞음
        this.key = indexList.stream().limit(6).map { i: Int? ->
            String.format(
                "%c", numList[i!!]
            )
        }.collect(Collectors.joining())
    }
}