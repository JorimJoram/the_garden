package com.sesac.climb_mates.service

import com.sesac.climb_mates.data.grouping.Grouping
import com.sesac.climb_mates.data.grouping.GroupingRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class GroupingService(
    private val groupingRepository: GroupingRepository
) {
    fun getGroupingList(): List<Grouping> {
        return groupingRepository.findAll()
    }

    fun getGroupingById(groupingId:Long): Grouping {
        return groupingRepository.findById(groupingId).get()
    }

    fun getGroupingByDate(date:LocalDate): List<Grouping> {
        val startOfDay = date.atStartOfDay()
        val endOfDay = date.atTime(23, 59, 59)
        return groupingRepository.findByCreatedDateBetween(startOfDay, endOfDay)
    }

    fun getGroupingByStoreIdAndDate(storeId:Long, date:LocalDate): List<Grouping> {
        val startOfDay = date.atStartOfDay()
        val endOfDay = date.atTime(23, 59, 59)
        return groupingRepository.findByStoreIdAndCreatedDateBetween(storeId, startOfDay, endOfDay)
    }

    fun createGrouping(grouping: Grouping): Grouping {
        return groupingRepository.save(grouping)
    }
}