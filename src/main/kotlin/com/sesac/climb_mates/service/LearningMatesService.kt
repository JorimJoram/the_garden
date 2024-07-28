package com.sesac.climb_mates.service

import com.sesac.climb_mates.data.account.AccountRepository
import com.sesac.climb_mates.data.learning_mates.*
import org.springframework.stereotype.Service

@Service
class LearningMatesService(
    private val learningMatesRepository: LearningMatesRepository,
    private val learningApplicantRepository: LearningApplicantRepository,
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

    fun getLearningMatesById(id: Long): LearningMates {
        return learningMatesRepository.findById(id).get()
    }

    fun getApplicantListById(learningId: Long): List<LearningApplicant> {
        return learningApplicantRepository.findByLearningMatesId(learningId)
    }

    fun createLearningApplicant(learningId: Long, username: String): LearningApplicant {
        return learningApplicantRepository.save(
            LearningApplicant(
                account = accountRepository.findByUsername(username).get(),
                learningMates = learningMatesRepository.findById(learningId).get()
            )
        )
    }

    fun deleteLearningApplicant(learningId: Long, username: String) {
        return learningApplicantRepository.deleteByAccountUsernameAndLearningMatesId(username, learningId)
    }
}