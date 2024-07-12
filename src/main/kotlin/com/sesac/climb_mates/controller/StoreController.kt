package com.sesac.climb_mates.controller

import com.sesac.climb_mates.data.store.StoreTime
import com.sesac.climb_mates.data.store.img.StoreImage
import com.sesac.climb_mates.service.StoreService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable

@Controller
@RequestMapping("/store")
class StoreController(
    private val storeService: StoreService
) {
    @GetMapping("/info/{storeId}")
    fun infoPage(@PathVariable("storeId") storeId:Long, model: Model): String {
        val storeData =  storeService.getStoreById(storeId)
        val menuData = storeService.getMenuByStoreId(storeId)
        val storeTimeData = storeService.getStoreTimeByStoreId(storeId).orElseGet {
            StoreTime(
                store = storeService.getStoreById(storeId),
                startTime = "10:00",
                endTime = "18:00",
                DoW = "월-금",
                breakEnd = "",
                breakStart = ""
            )
        }
        val storeImage = storeService.getStoreImageByStoreId(storeId).orElseGet {
            StoreImage(
                store = storeService.getStoreById(storeId),
                path = "/img/store/burgers_taein.png",
                size= 0
            )
        }

        model.addAttribute("storeData", storeData)
        model.addAttribute("menuData", menuData)
        model.addAttribute("storeTimeData", storeTimeData)
        model.addAttribute("storeImage", storeImage)

        return "store/info"
    }
}