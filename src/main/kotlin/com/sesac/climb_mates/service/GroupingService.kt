package com.sesac.climb_mates.service

import com.sesac.climb_mates.data.account.Account
import com.sesac.climb_mates.data.grouping.*
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class GroupingService(
    private val groupingRepository: GroupingRepository,
    private val groupingApplicantRepository: GroupingApplicantRepository,
    private val groupingReviewRepository: GroupingReviewRepository
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
        return groupingRepository.findByMeetingDateBetween(startOfDay, endOfDay)
    }

    fun getGroupingByStoreIdAndDate(storeId:Long, date:LocalDate): List<Grouping> {
        val startOfDay = date.atStartOfDay()
        val endOfDay = date.atTime(23, 59, 59)
        return groupingRepository.findByStoreIdAndMeetingDateBetween(storeId, startOfDay, endOfDay)
    }

    fun getGroupingGreaterThanEqual(date:LocalDate):List<Grouping>{
        val startOfDay = date.atStartOfDay()
        return groupingRepository.findByMeetingDateGreaterThanEqualOrderByMeetingDateAsc(startOfDay)
    }

    fun getGroupingApplicantListByGroupingId(groupingId:Long): List<GroupingApplicant> {
        return groupingApplicantRepository.findByGroupingId(groupingId)
    }

    fun getGroupingApplicantList(username:String): List<GroupingApplicant> {
        return groupingApplicantRepository.findByAccountUsername(username)
    }

    fun createGrouping(grouping: Grouping): Grouping {
        return groupingRepository.save(grouping)
    }

    fun createGroupingApplicant(groupingApplicant: GroupingApplicant): Any {
        return groupingApplicantRepository.save(groupingApplicant)
    }

    fun isApplicant(groupingId: Long, account:Account): Boolean {
        groupingApplicantRepository.findByGroupingId(groupingId).forEach {
            if(it.account == account){
                println("있음")
                return true
            }
        }
        return false
    }

    fun getGroupingReviewListByGroupingId(groupId: Long): List<GroupingReview> {
        return groupingReviewRepository.findByGroupingId(groupId)
    }

    fun createGroupingReview(groupingReview: GroupingReview): GroupingReview {
        return groupingReviewRepository.save(groupingReview)
    }
}