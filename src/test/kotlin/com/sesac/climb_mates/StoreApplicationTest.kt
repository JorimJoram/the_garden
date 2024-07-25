package com.sesac.climb_mates

import com.opencsv.CSVReader
import com.sesac.climb_mates.data.store.Menu
import com.sesac.climb_mates.data.store.Store
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
}