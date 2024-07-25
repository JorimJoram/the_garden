package com.sesac.climb_mates.data.grouping

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface GroupingRepository:JpaRepository<Grouping, Long> {
    fun findByMeetingDateBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<Grouping>
    fun findByMeetingDateGreaterThanEqualOrderByMeetingDateAsc(startDate:LocalDateTime): List<Grouping>

    fun findByStoreIdAndMeetingDateBetween(storeId:Long, startDate:LocalDateTime, endDate: LocalDateTime): List<Grouping>
}