package com.sesac.climb_mates

import com.sesac.climb_mates.data.grouping.Grouping
import com.sesac.climb_mates.data.grouping.GroupingApplicant
import com.sesac.climb_mates.service.AccountService
import com.sesac.climb_mates.service.GroupingService
import com.sesac.climb_mates.service.StoreService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.Test

@SpringBootTest
class GroupingApplicationTest(
    @Autowired private val accountService: AccountService,
    @Autowired private val storeService: StoreService,
    @Autowired private val groupingService: GroupingService
) {
    @Test
    fun createGrouping(){
        println(
            groupingService.createGrouping(
                Grouping(
                    title="셀러디 레이드 구합니다!",
                    content="그거 아세요 동덕 여대 주변에 셀러디가 있다는 사실을?!",
                    account = accountService.getAccountByUsername("test1").get(),
                    store = storeService.getStoreById(1L),
                    meetingDate = LocalDateTime.now()
                )
            )
        )
    }

    @Test
    fun getGroupingList(){
        println(groupingService.getGroupingList())
    }

    @Test
    fun getGroupingById(){
        println(groupingService.getGroupingById(1L))
    }

    @Test
    fun getGroupingListByDate(){
        val dateString = "2024-07-14"
        val localDate: LocalDate = LocalDate.parse(dateString)
        println(
            groupingService.getGroupingByDate(localDate)
        )
    }

    @Test
    fun dateDefaultTest(){
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDate = currentDate.format(formatter)
        println(formattedDate)
    }

    @Test
    fun getGroupingStoreIdAndDate(){
        val dateString = "2024-07-14"
        val localDate: LocalDate = LocalDate.parse(dateString)
        val result = groupingService.getGroupingByStoreIdAndDate(1L, localDate)
        print(result)
    }

    @Test
    fun createSpecialDate(){
        val specificDate = LocalDateTime.of(2024,7,26, 0, 0)
        println(groupingService.createGrouping(
            Grouping(
                title = "Test 26",
                content = "TEST: test26",
                account = accountService.getAccountByUsername("test5").get(),
                store = storeService.getStoreById(1L),
                meetingDate = specificDate
            )
        ))
    }

    @Test
    fun getGroupingGreaterThanEqual(){
        val currentDate = LocalDate.now()
        groupingService.getGroupingGreaterThanEqual(currentDate).forEach {
            println(it)
        }
    }

    @Test
    fun createGroupingApplicant(){
        println(
            groupingService.createGroupingApplicant(
                GroupingApplicant(
                    grouping = groupingService.getGroupingById(3L),
                    account =  accountService.getAccountByUsername("test1").get(),
                )
            )
        )
    }
}