package com.sesac.climb_mates

import com.sesac.climb_mates.data.account.Account
import com.sesac.climb_mates.data.store.Menu
import com.sesac.climb_mates.data.store.Store
import com.sesac.climb_mates.data.store.StoreTime
import com.sesac.climb_mates.service.AccountService
import com.sesac.climb_mates.service.StoreService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ClimbMatesApplicationTests(
	@Autowired private val storeService: StoreService,
	@Autowired private val accountService: AccountService
) {

	@Test
	fun contextLoads() {
	}

	@Test
	fun createStore(){
		val nameList = mutableListOf("제나키친", "산촌기사식당", "강남부대찌개", "명가추어탕보리밥", "금화왕돈까스", "스시빈", "샤브로21", "다오미김밥", "스시현")
		val locationList = mutableListOf("서울 성북구 화랑로11길 23 2층 제나키친", "서울 성북구 오패산로 2", "서울 성북구 화랑로5길 41", "서울 성북구 오패산로 6-9", "서울 성북구 오패산로 13 1층", "서울 성북구 화랑로 105", "서울 성북구 화랑로 95 1층 좌측호", "서울 성북구 오패산로 51", "서울 성북구 오패산로4길 32",)
		val latList = mutableListOf("37.6034124", "37.6029048", "37.6044350", "37.6034146", "37.6034658","37.6038651", "37.6032830", "37.6067811", "37.6041487")
		val lonList = mutableListOf("127.0416930", "127.0385257", "127.0391857", "127.0379090", "127.0371568","127.0432908", "127.0424731", "127.0365072", "127.0394444")
		val attrList = mutableListOf("1839481919", "18916029", "32094307", "1544576697", "1653595806", "1221575218", "1404196964", "38411769", "1811359116")
		val styleList = mutableListOf("경양식", "한식", "한식","한식","경양식", "일식", "한식", "분식", "일식")

		for(i: Int in 0..< nameList.size){
			val store = Store(
				name = nameList[i],
				location = locationList[i],
				lat = latList[i],
				lon = lonList[i],
				attr = attrList[i],
				style= styleList[i]
			)
			println(storeService.createStore(store))
		}
	}

	@Test
	fun getAllStore(){
		//val storeList = storeService.getAllStore()
//		for(i:Int in storeList.indices){
//			print(i)
//			println(storeList[i])
//		}
	}

	@Test
	fun createAccount(){
		val account = Account(
			username = "jorimjoram",
			password = "1234",
			classRoom = "성북DT",
			email = "wkdgyfla97@naver.com",
			role = "ADMIN"
		)
		accountService.createAccount(account)
	}

	@Test
	fun createSingleStore(){
		val store = Store(
			name = "버거스태인",
			location = "서울특별시 성북구 오패산로 23",
			lat = "37.604465",
			lon = "127.037200",
			attr = "1193819667",
			style= "경양식"
		)
		println(storeService.createStore(store))
	}
	@Test
	fun createMenu(){
		val store = storeService.getStoreById(52)
		val menuNameList = mutableListOf("파파버거", "마마버거","호두 떡갈비 버거", "당근 버가", "매콤 버거", "크림치즈 떡갈비 버거")
		val priceList = mutableListOf(7800, 8800, 9800, 10800, 11800, 11800)
		for(i in menuNameList.indices){
			storeService.createMenu(
				Menu(
					store = store,
					name = menuNameList[i],
					price = priceList[i],
				)
			)
			print(menuNameList[i])
		}
	}

	@Test
	fun createStoreTime(){
		val store = storeService.getStoreById(52)
		storeService.createStoreTime(
			StoreTime(
				store = store,
				startTime = "11:30",
				endTime = "20:30",
				DoW = "화 ~ 일",
				breakStart = "15:00",
				breakEnd = "17:00"
			)
		)
	}
}
