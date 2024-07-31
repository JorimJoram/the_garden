package com.sesac.climb_mates.controller

import com.sesac.climb_mates.service.AccountService
import com.sesac.climb_mates.service.GroupingService
import jakarta.servlet.http.HttpSession
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Controller
@RequestMapping("/grouping")
class GroupingController(
    private val groupingService: GroupingService,
    private val accountService: AccountService,
    private val session:HttpSession
) {
    @GetMapping("/list")
    fun groupingListPage(@RequestParam(name="date", defaultValue = "none")date:String, @AuthenticationPrincipal user:User, model: Model): String {
        var requestDate = date
        if(requestDate == "none"){
            requestDate = defaultLocalDate()
        }
        val groupingList = groupingService.getGroupingGreaterThanEqual(LocalDate.parse(requestDate))
        val applyList = groupingService.getGroupingApplicantList(user.username) //내가 신청했던 리스트를 가져온 거임
        groupingList.forEach {
            val applicantList = groupingService.getGroupingApplicantListByGroupingId(it.id!!)
            it.applicantList = applicantList
            it.formattedDate = getFormattedDate(it.meetingDate)
            println(it.ment)
        }
        //내가 신청했던 걸 담은 리스트와 담지 않았던 리스트를 비교해보자 -> GPT에게 도움 받은 버전
        if (groupingList.isNotEmpty()) {
            // applyList를 Set으로 변환하여 빠른 검색이 가능하도록 합니다.
            val applySet = applyList.toSet()

            // groupingList를 순회하며, 각 항목의 applicantList가 applySet에 포함되어 있는지 확인합니다.
            for (group in groupingList) {
                group.ment=if(group.applicantList.isNotEmpty()){ "${group.applicantList.random().account.username}님 포함 ${group.applicantList.size}명 참가예정" }else{ "" }
                group.isApply = group.applicantList.any { it in applySet }
            }
        }

        model.addAttribute("groupList", groupingList)
        return "grouping/list"
    }

    @GetMapping("/detail/{id}")
    fun groupingDetailPage(@PathVariable(name = "id")id:Long, @AuthenticationPrincipal user:User, model: Model): String {
        val accountData = accountService.getAccountByUsername(user.username).get()
        val groupingDetail = groupingService.getGroupingById(id)
        val userSession = session.getAttribute("session_user")
        groupingDetail.formattedDate = getFormattedDate(groupingDetail.meetingDate)
        groupingDetail.isApply = !groupingService.isApplicant(id, accountService.getAccountByUsername(user.username).get())
        val isMine = accountData.username == groupingDetail.account.username //true -> 내가 적은거임

        model.addAttribute("Account", accountData)
        model.addAttribute("grouping", groupingDetail)
        model.addAttribute("session_user", userSession)
        model.addAttribute("isMine", isMine)
        model.addAttribute("my_username", user.username)
        return "grouping/detail"
    }

    private fun defaultLocalDate(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = currentDate.format(formatter)
        return formattedDate
    }

    private fun getFormattedDate(date: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        return date.format(formatter)
    }

    @GetMapping("/create")
    private fun createPage(): String {
        return "grouping/create"
    }
}