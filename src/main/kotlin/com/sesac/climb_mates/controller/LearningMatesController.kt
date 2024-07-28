package com.sesac.climb_mates.controller

import com.sesac.climb_mates.service.LearningMatesService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/learning")
class LearningMatesController(
    private val learningMatesService: LearningMatesService
) {
    @GetMapping("/create")
    fun createPage(): String {
        return "learning/create"
    }
    @GetMapping("/detail/{id}")
    fun detailPage(@PathVariable("id")id:Long, model: Model): String {
        val learning = learningMatesService.getLearningMatesById(id)
        model.addAttribute("learning", learning)
        return "learning/detail"
    }
}