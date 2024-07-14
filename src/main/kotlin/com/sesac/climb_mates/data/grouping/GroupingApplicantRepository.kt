package com.sesac.climb_mates.data.grouping

import org.springframework.data.jpa.repository.JpaRepository

interface GroupingApplicantRepository: JpaRepository<GroupingApplicant, Long> {
    fun findByGroupingId(groupingId:Long): List<GroupingApplicant>
    fun findByAccountId(accountId:Long): List<GroupingApplicant>
}