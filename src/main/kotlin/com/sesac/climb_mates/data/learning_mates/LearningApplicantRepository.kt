package com.sesac.climb_mates.data.learning_mates

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository

interface LearningApplicantRepository:JpaRepository<LearningApplicant, Long> {
    fun findByLearningMatesId(learningMatesId:Long):List<LearningApplicant>
    @Transactional
    fun deleteByAccountUsernameAndLearningMatesId(username: String, learningMatesId: Long)
}