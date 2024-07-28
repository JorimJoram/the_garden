package com.sesac.climb_mates.service

import com.sesac.climb_mates.data.account.AccountRepository
import com.sesac.climb_mates.data.learning_mates.LearningMates
import com.sesac.climb_mates.data.learning_mates.LearningMatesDTO
import com.sesac.climb_mates.data.learning_mates.LearningMatesRepository
import org.springframework.stereotype.Service

@Service
class LearningMatesService(
    private val learningMatesRepository: LearningMatesRepository,
    private val accountRepository: AccountRepository
) {
    fun createLearningMates(dto:LearningMatesDTO, username:String): LearningMates {
        return learningMatesRepository.save(
            LearningMates(
                account = accountRepository.findByUsername(username).get(),
                applicantCnt = dto.applicantCnt,
                style = dto.style,
                freq = dto.freq,
                title = dto.title,
                content = dto.content
            )
        )
    }
}