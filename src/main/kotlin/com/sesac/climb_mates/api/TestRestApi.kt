package com.sesac.climb_mates.api

import com.sesac.climb_mates.data.test.GoogleInfResponse
import com.sesac.climb_mates.data.test.GoogleRequest
import com.sesac.climb_mates.data.test.GoogleResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import java.util.*


@RestController
@RequestMapping("/api")
class TestRestApi {
    @Value("\${google.client.id}")
    private lateinit var googleClientId: String

    @Value("\${google.client.pw}")
    private lateinit var googleClientPw: String

    @PostMapping("/v2/oauth2/google")
    fun loginUrlGoogle(): String {
        val reqUrl = "https://accounts.google.com/o/oauth2/v2/auth?client_id=$googleClientId" +
                "&redirect_uri=http://localhost:12571/api/v2/oauth2/google" +
                "&response_type=code" +
                "&scope=email%20profile%20openid" +
                "&access_type=offline"
        return reqUrl
    }

    @GetMapping("/v2/oauth2/google")
    fun loginGoogle(@RequestParam(value = "code") authCode: String): String {
        val restTemplate = RestTemplate()
        val googleOAuthRequestParam = GoogleRequest(
            clientId = googleClientId,
            clientSecret = googleClientPw,
            code = authCode,
            redirectUri = "http://localhost:12571/api/v2/oauth2/google",
            grantType = "authorization_code",
            scope = "email profile openid",
            accessType = "offline",
            responseType = "code"
        )
        val resultEntity: ResponseEntity<GoogleResponse> = restTemplate.postForEntity(
            "https://oauth2.googleapis.com/token",
            googleOAuthRequestParam, GoogleResponse::class.java
        )
        val jwtToken: String = resultEntity.body!!.id_token//.getId_token()
        val map: MutableMap<String, String> = HashMap()
        map["id_token"] = jwtToken
        val resultEntity2: ResponseEntity<GoogleInfResponse> = restTemplate.postForEntity(
            "https://oauth2.googleapis.com/tokeninfo",
            map, GoogleInfResponse::class.java
        )
        val email: String = resultEntity2.body!!.email
        return email
    }
}