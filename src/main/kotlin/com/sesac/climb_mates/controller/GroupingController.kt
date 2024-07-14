package com.sesac.climb_mates.controller

import com.sesac.climb_mates.service.GroupingService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Controller
@RequestMapping("/grouping")
class GroupingController(
    private val groupingService: GroupingService
) {
    @GetMapping("/list")
    fun groupingListPage(@RequestParam(name="date", defaultValue = "none")date:String, model: Model): String {
        var requestDate = date
        if(requestDate == "none"){
            requestDate = defaultLocalDate()
        }
        val groupingList = groupingService.getGroupingByDate(LocalDate.parse(requestDate))
        model.addAttribute("groupList", groupingList)
        return "grouping/list"
    }

    @GetMapping("/detail/{id}")
    fun groupingDetailPage(@PathVariable(name = "id")id:Long, model: Model): String {
        val groupingDetail = groupingService.getGroupingById(id)
        model.addAttribute("grouping", groupingDetail)
        return "grouping/detail"
    }

    private fun defaultLocalDate(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = currentDate.format(formatter)
        return formattedDate
    }
}