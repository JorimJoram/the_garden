package com.sesac.climb_mates.api

import com.sesac.climb_mates.data.learning_mates.LearningMates
import com.sesac.climb_mates.data.learning_mates.LearningMatesDTO
import com.sesac.climb_mates.service.LearningMatesService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/learning/api")
class LearningMatesRestApi(
    private val learningMatesService: LearningMatesService
) {
    @PostMapping("/create")
    fun createLearningMates(@RequestBody dto:LearningMatesDTO, @AuthenticationPrincipal user: User): LearningMates {
        return learningMatesService.createLearningMates(dto, user.username)
    }
}