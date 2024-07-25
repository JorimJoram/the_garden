package com.sesac.climb_mates.data.grouping

import jakarta.persistence.Id
import org.springframework.data.jpa.repository.JpaRepository

interface GroupingReviewRepository:JpaRepository<GroupingReview, Long> {
    fun findByGroupingId(groupingId: Long): List<GroupingReview>
}