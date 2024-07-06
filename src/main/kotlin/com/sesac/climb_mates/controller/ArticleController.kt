package com.sesac.climb_mates.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/article")
class ArticleController {
    @GetMapping("/list")
    fun getArticleList(@RequestParam(name="page", defaultValue = "0")page:Int): Int {
        return page
    }
    @GetMapping("/test")
    fun test(): String {
        return "test"
    }
}