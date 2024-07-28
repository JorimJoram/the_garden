package com.sesac.climb_mates.data.learning_mates

import org.springframework.data.jpa.repository.JpaRepository

interface LearningReviewRepository:JpaRepository<LearningReview, Long> {
    fun findByLearningMatesId(learningMatesId:Long):List<LearningReview>
}