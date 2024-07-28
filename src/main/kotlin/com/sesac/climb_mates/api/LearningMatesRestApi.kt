package com.sesac.climb_mates.api

import com.sesac.climb_mates.data.learning_mates.LearningApplicant
import com.sesac.climb_mates.data.learning_mates.LearningMates
import com.sesac.climb_mates.data.learning_mates.LearningMatesDTO
import com.sesac.climb_mates.service.AccountService
import com.sesac.climb_mates.service.LearningMatesService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/learning/api")
class LearningMatesRestApi(
    private val learningMatesService: LearningMatesService,
    private val accountService: AccountService
) {
    @PostMapping("/create")
    fun createLearningMates(@RequestBody dto:LearningMatesDTO, @AuthenticationPrincipal user: User): LearningMates {
        return learningMatesService.createLearningMates(dto, user.username)
    }

    @PostMapping("/applicant/create/{learningId}")
    fun createLearningApplicant(@PathVariable(name="learningId")learningId: Long, @AuthenticationPrincipal user: User): LearningApplicant {
        return learningMatesService.createLearningApplicant(learningId, user.username)
    }

    @GetMapping("/apply-list")
    fun getApplicantList(@RequestParam(name="learningId") learningId:Long): List<LearningApplicant> {
        return learningMatesService.getApplicantListById(learningId)
    }

    @GetMapping("/apply-cert")
    fun certForApplyGrouping(@AuthenticationPrincipal user: User, @RequestParam(name="learningId")learningId:Long): Boolean {
        val userAccount = accountService.getAccountByUsername(user.username).get()
        learningMatesService.getApplicantListById(learningId).forEach {
            if (it.account == userAccount)
                return false
        } //동작 정상 확인
        return true
    }

    @DeleteMapping("/applicant/del/{learningId}")
    fun deleteGroupingApplicant(@PathVariable(name="learningId")learningId: Long, @AuthenticationPrincipal user: User){
        return learningMatesService.deleteLearningApplicant(learningId, user.username)
    }
}