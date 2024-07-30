package com.sesac.climb_mates.controller

import com.sesac.climb_mates.service.LearningMatesService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Controller
@RequestMapping("/learning")
class LearningMatesController(
    private val learningMatesService: LearningMatesService
) {
    @GetMapping("/create")
    fun createPage(): String {
        return "learning/create"
    }
    @GetMapping("/list")
    fun listPage(model: Model): String {
        val learningList = learningMatesService.getAllList()
        var ments = ""
        learningList.forEach{
            it.applyList = learningMatesService.getApplicantListById(it.id!!)
            it.applyCnt = it.applyList!!.size
            it.formattedDate = formatLocalDateTimeToSeconds(it.createdDate)
            it.applicantComment = when(it.applyCnt){
                0 -> "러닝 메이트가 필요해요!"
                else -> "${it.applyCnt}명이 참여할 예정이에요!"
            }
        }
        model.addAttribute("learningMates", learningList)

        return "learning/list"
    }
    @GetMapping("/detail/{id}")
    fun detailPage(@PathVariable("id")id:Long, model: Model): String {
        val learning = learningMatesService.getLearningMatesById(id)
        model.addAttribute("learning", learning)
        return "learning/detail"
    }

    private fun formatLocalDateTimeToSeconds(localDateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return localDateTime.format(formatter)
    }

}