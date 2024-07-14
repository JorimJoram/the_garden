package com.sesac.climb_mates.data.grouping

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface GroupingRepository:JpaRepository<Grouping, Long> {
    fun findByCreatedDateBetween(startDate: LocalDateTime, endDate: LocalDateTime): List<Grouping>
    fun findByStoreIdAndCreatedDateBetween(storeId:Long, startDate:LocalDateTime, endDate: LocalDateTime): List<Grouping>
}