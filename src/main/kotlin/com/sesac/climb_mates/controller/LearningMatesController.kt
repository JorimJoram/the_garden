package com.sesac.climb_mates.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/learning")
class LearningMatesController {
    @GetMapping("/create")
    fun createPage(): String {
        return "learning/create"
    }
}