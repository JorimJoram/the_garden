package com.sesac.climb_mates.service

import com.sesac.climb_mates.data.account.Account
import com.sesac.climb_mates.data.account.AccountRepository
import com.sesac.climb_mates.data.grouping.*
import com.sesac.climb_mates.data.store.StoreRepository
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class GroupingService(
    private val groupingRepository: GroupingRepository,
    private val groupingApplicantRepository: GroupingApplicantRepository,
    private val groupingReviewRepository: GroupingReviewRepository,
    private val storeRepository: StoreRepository,
    private val accountRepository: AccountRepository
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

    fun createGrouping(dto: GroupingDTO, username: String): Grouping {
        return groupingRepository.save(Grouping(
            title = dto.title,
            content = dto.content,
            account = accountRepository.findByUsername(username).get(),
            store = storeRepository.findById(dto.storeId).get(),
            meetingDate = toLocalDateTime(dto.meetingDate)
        ))
    }

    private fun toLocalDateTime(date:String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        val localDateTime = LocalDateTime.parse(date, formatter)
        return localDateTime
    }

    fun createGroupingApplicant(groupId:Long, username:String): GroupingApplicant {
        return groupingApplicantRepository.save(GroupingApplicant(
            account = accountRepository.findByUsername(username).get(),
            grouping =  groupingRepository.findById(groupId).get(),
        ))
    }

    fun deleteGroupingApplicant(groupId: Long, username: String){
        return groupingApplicantRepository.deleteByAccountUsernameAndGroupingId(username, groupId)
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

    fun createGroupingReview(review: GroupingReviewDTO, user: User): GroupingReview {
        return groupingReviewRepository.save(
            GroupingReview(
                grouping = groupingRepository.findById(review.groupId).get(),
                account = accountRepository.findByUsername(user.username).get(),
                content = review.content
            )
        )
    }
}