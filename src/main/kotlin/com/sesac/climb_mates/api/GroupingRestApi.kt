package com.sesac.climb_mates.api

import com.sesac.climb_mates.data.grouping.*
import com.sesac.climb_mates.service.AccountService
import com.sesac.climb_mates.service.GroupingService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/grouping/api")
class GroupingRestApi(
    private val groupingService: GroupingService,
    private val accountService: AccountService
) {
    @GetMapping("/my/apply-list")
    fun getMyApplicantList(@AuthenticationPrincipal user: User): List<GroupingApplicant> {
        return groupingService.getGroupingApplicantList(user.username)
    }

    @GetMapping("/group/apply-list")
    fun getGroupingApplicantList(@RequestParam(name="groupId")groupId: Long): List<GroupingApplicant> {
        return groupingService.getGroupingApplicantListByGroupingId(groupId)
    }

    /**
     * 신청을 했는지 알려주는 메서드입니다.
     * true -> 신청가능상태 / false -> 이미 신청한상태
     */
    @GetMapping("/apply-cert")
    fun certForApplyGrouping(@AuthenticationPrincipal user: User, @RequestParam(name="groupId")groupId:Long): Boolean {
        val userAccount = accountService.getAccountByUsername(user.username).get()
        groupingService.getGroupingApplicantListByGroupingId(groupId).forEach {
            if (it.account == userAccount)
                return false
        } //동작 정상 확인
        return true
    }

    @GetMapping("/review/list")
    fun getGroupingReviewList(@RequestParam(name="groupId")groupId: Long): List<GroupingReview> {
        return groupingService.getGroupingReviewListByGroupingId(groupId)
    }

    @PostMapping("/create")
    fun createGrouping(@RequestBody groupingDTO: GroupingDTO, @AuthenticationPrincipal user: User): Grouping {
        println("DTO: ${groupingDTO}")
        return groupingService.createGrouping(groupingDTO, user.username)
    }

    @PostMapping("/review/create")
    fun createGroupingReview(@RequestBody review: GroupingReviewDTO, @AuthenticationPrincipal user: User): Long {
        return groupingService.createGroupingReview(review, user).id!!
    }
    @PostMapping("/applicant/create/{groupId}")
    fun createGroupingApplicant(@PathVariable(name="groupId")groupId: Long, @AuthenticationPrincipal user: User): GroupingApplicant {
        return groupingService.createGroupingApplicant(groupId, user.username);
    }
    @DeleteMapping("/applicant/del/{groupId}")
    fun deleteGroupingApplicant(@PathVariable(name="groupId")groupId: Long, @AuthenticationPrincipal user: User){
        return groupingService.deleteGroupingApplicant(groupId, user.username)
    }
}