package com.sesac.climb_mates

import com.opencsv.CSVReader
import com.sesac.climb_mates.data.store.Menu
import com.sesac.climb_mates.data.store.Store
import com.sesac.climb_mates.data.store.img.StoreImage
import com.sesac.climb_mates.service.AccountService
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
class CsvReadTest(
    @Autowired private val resourceLoader: ResourceLoader,
    @Autowired private val storeService: StoreService
) {
    /**
     * 식당명0,음식점종류1,제페유무2,식대가능유무3,메뉴4,가격5,도보6,전화번호7,영업날짜/시간8,지도url뒤 숫자만9,도로명주소10,사진 주소값11
     */
    @Test
    fun setStoreList(){
        val storeSet:MutableSet<MutableList<String>> = mutableSetOf()
        readCSV("the_garden").forEach{
            storeSet.add(
                mutableListOf(it[0], it[10], it[1], it[2], it[3], it[9])
            )
        }
        val locationList = storeSet.toList() //가게 정보 구분 완료
        saveStoreList(locationList)
    }

    @Test
    fun setMenuList(){
        val menuList:MutableList<MutableList<String>> = mutableListOf()
        readCSV("the_garden").forEach{
            menuList.add(
                mutableListOf(it[0], it[4], it[5])
            )
        }
        menuList.forEach {
            storeService.createMenu(
                Menu(
                    store = storeService.getStoreByName(it[0]),
                    name = it[1],
                    price = it[2].toInt()
                )
            )
        }
    }

    @Test
    fun setStoreImage(){
        val storeImageList:MutableList<MutableList<String>> = mutableListOf()
        readCSV("the_garden").forEach{
            if(it[11].isBlank())
                return@forEach
            storeImageList.add(
                mutableListOf(it[0], it[11])
            )
        }
        storeImageList.forEach{
            println("${
                storeService.createStoreImage(
                    StoreImage(
                        store = storeService.getStoreByName(it[0]),
                        path=it[1],
                        size = 0
                    )
                )
            }")
        }
    }

    private fun saveStoreList(storeList:List<MutableList<String>>){
        storeList.forEach {
            val isRegistered = storeService.getStoreByName(it[0])
            if(isRegistered.id != -1L)
                return@forEach
            val loc = getGeolocation(it[1])!!
            val lat = loc.first
            val lon = loc.second
            println("${
                storeService.createStore(
                    Store(
                        name = it[0],
                        location = it[1],
                        attr = it[5],
                        isZero = it[3].toInt(),
                        isSupport = it[4].toInt(),
                        style = it[2],
                        lat = lat,
                        lon = lon
                    )
                )
            }")
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
}