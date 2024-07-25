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
        val applyList = groupingService.getGroupingApplicantList(user.username)
        groupingList.forEach {
            val applicantList = groupingService.getGroupingApplicantListByGroupingId(it.id!!)
            it.applicantList = applicantList
            it.formattedDate = getFormattedDate(it.meetingDate)

        }
        //내가 신청했던 걸 담은 리스트와 담지 않았던 리스트를 비교해보자
        if(groupingList.isNotEmpty()){
            for(i:Int in applyList.indices) {
                if(groupingList[i].applicantList.isNotEmpty())
                    groupingList[i].isApply = applyList[i] in groupingList[i].applicantList
            }
        }

        model.addAttribute("groupList", groupingList)
        return "grouping/list"
    }

    @GetMapping("/detail/{id}")
    fun groupingDetailPage(@PathVariable(name = "id")id:Long, @AuthenticationPrincipal user:User, model: Model): String {
        val accountData = accountService.getAccountByUsername(user.username).get()
        println("Account: $accountData")
        val groupingDetail = groupingService.getGroupingById(id)
        groupingDetail.formattedDate = getFormattedDate(groupingDetail.meetingDate)
        groupingDetail.isApply = !groupingService.isApplicant(id, accountService.getAccountByUsername(user.username).get())

        model.addAttribute("Account", accountData)
        model.addAttribute("grouping", groupingDetail)
        return "grouping/detail"
    }

    private fun defaultLocalDate(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = currentDate.format(formatter)
        return formattedDate
    }

    private fun getFormattedDate(date: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return date.format(formatter)
    }
}