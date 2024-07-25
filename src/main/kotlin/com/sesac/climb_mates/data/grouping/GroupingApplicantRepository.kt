package com.sesac.climb_mates.data.grouping

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository

interface GroupingApplicantRepository: JpaRepository<GroupingApplicant, Long> {
    fun findByGroupingId(groupingId:Long): List<GroupingApplicant>
    fun findByAccountId(accountId:Long): List<GroupingApplicant>
    fun findByAccountUsername(accountUsername:String):List<GroupingApplicant>

    @Transactional
    fun deleteByAccountUsernameAndGroupingId(accountUsername:String, groupingId:Long):Unit
}