package com.sesac.climb_mates

import com.opencsv.CSVReader
import com.sesac.climb_mates.data.store.Menu
import com.sesac.climb_mates.data.store.Store
import com.sesac.climb_mates.data.store.time.StoreTime
import com.sesac.climb_mates.service.StoreService
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ResourceLoader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

@SpringBootTest
class StoreApplicationTest(
    @Autowired private val resourceLoader: ResourceLoader,
    @Autowired private val storeService: StoreService
) {
    /**
     * 가게명(0) 음식점유형(1) 제로페이 유무(2) 식대가능유무(3) 메뉴(4) 가격(5) attr(6) 주소(7) 사진주소값(8)
     */
    @Test
    fun setStoreList(){
        val fileName = "storeAndMenu"
        val csvList = readCSV(fileName)
        csvList.forEach{
            val isRegistered = storeService.getStoreByName(it[0])
            if(isRegistered.id != -1L)
                return@forEach
            val loc = getGeolocation(it[5])!!
            val lat = loc.first
            val lon = loc.second
            println("${
                storeService.createStore(
                    Store(
                        name = it[0],
                        location = it[5],
                        attr = it[4],
                        isZero = it[2].toInt(),
                        isSupport = it[3].toInt(),
                        style = it[1],
                        lat = lat,
                        lon = lon
                    )
                )
            } 작업 완료")
        }
    }

    private fun readCSV(fileName:String): MutableList<List<String>> {
        val resource = resourceLoader.getResource("classpath:/static/data/${fileName}.csv")
        val resultList:MutableList<List<String>> = mutableListOf()
        CSVReader(InputStreamReader(resource.inputStream, StandardCharsets.UTF_8)).use { csvReader ->
            val lines: List<Array<String?>> = csvReader.readAll()

            for (line in lines) {
                val lineList = java.util.List.of(*line)
                resultList.add(lineList as List<String>)
            }
        }
        return resultList
    }

    private fun getGeolocation(location:String): Pair<String, String>? {
        val clientId = "9eujxr3k62"
        val clientSecret = "219F0RJXzRSECp2I2tPm5vCMmxgmR1WkjRBBsTJj"
        val encodedLoc = java.net.URLEncoder.encode(location, "UTF-8")
        val urlString = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=$encodedLoc"

        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId)
        connection.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret)
        return try{
            val responseCode = connection.responseCode
            if(responseCode == HttpURLConnection.HTTP_OK){
                val responseBody = connection.inputStream.bufferedReader().use { it.readText() }
                val jsonResponse = JSONObject(responseBody)
                if(jsonResponse.getJSONObject("meta").getInt("totalCount") == 1){
                    val lat = jsonResponse.getJSONArray("addresses").getJSONObject(0).getString("y")
                    val lon = jsonResponse.getJSONArray("addresses").getJSONObject(0).getString("x")
                    Pair(lat, lon)
                } else {
                    println("Location not exist")
                    null
                }
            } else {
                println("ERROR: $responseCode")
                null
            }
        }catch(e:Exception){
            e.printStackTrace()
            null
        }finally{
            connection.disconnect()
        }
    }

    @Test
    fun writeMenu(){
        val menuList = readMenuCSV("menuAndPrice")
        menuList.forEach {
            storeService.createMenu(
                Menu(
                    store = storeService.getStoreByName(it[0]),
                    name = it[1],
                    price = it[2].toInt(),
                    urlPath = it[3]
                )
            )
        }
    }

    private fun readMenuCSV(fileName:String): MutableList<List<String>> {
        val resource = resourceLoader.getResource("classpath:/static/data/${fileName}.csv")
        val resultList:MutableList<List<String>> = mutableListOf()
        CSVReader(InputStreamReader(resource.inputStream, StandardCharsets.UTF_8)).use { csvReader ->
            val lines: List<Array<String?>> = csvReader.readAll()

            for (line in lines) {
                val lineList = java.util.List.of(*line)
                resultList.add(lineList as List<String>)
            }
        }
        return resultList
    }
    @Test
    fun getSingleStore(){
        println(storeService.getStoreById(1L))
    }

    @Test
    fun createStoreTime(){
        val storeList = mutableListOf(
            "최고김밥", "제나키친", "장어세상", "원조나드리장터순대국", "미소야", "버거스태인", "먹자해장국", "OK능이마을", "샐러드프린세스", "포옹남 하월곡", "어멍식당", "송송식탁", "샤브로21", "명가해물짬뽕", "광수참치", "골라먹는맛", "개성손만두", "김만희떡볶이"
        )
        val dowList = mutableListOf(
            "매일", "월~금", "매일", "매일", "매일", "화~일", "매일", "매일", "매일", "매일", "월~토", "월~토", "매일", "화~일", "매일", "매일", "매일"," 월~토"
        )
        val startList = mutableListOf(
            "8:30", "11:00", "11:00", "07:00", "10:30", "11:30", "10:30", "11:00", "10:00", "11:00", "11:00","11:00","11:00", "10:40", "11:30", "11:00", "11:00", "12:00"
        )
        val endList = mutableListOf(
            "20:00", "22:00", "22:00", "24:00", "21:00", "20:30", "21:00", "22:00", "20:30", "21:00", "20:00","21:00", "22:00", "21:30", "23:00", "22:00", "22:00"
        )
        for(i:Int in storeList.indices){
            storeService.createStoreTime(
                StoreTime(
                    store = storeService.getStoreByName(storeList[i]),
                    startTime = startList[i],
                    endTime = endList[i],
                    DoW = dowList[i],
                )
            )
        }
    }
    @Test
    fun createSingleTime(){
        storeService.createStoreTime(
            StoreTime(
                store = storeService.getStoreById(18),
                startTime = "12:00",
                endTime = "22:00",
                DoW = "월~토",
            )
        )
    }
}