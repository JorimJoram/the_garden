package com.sesac.climb_mates.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
@RequestMapping("/api")
class TestRestApi {
    @GetMapping("/users")
    fun getAllUsers(): List<String> {
        return listOf<String>("John Doe","Jane Doe")
    }
}