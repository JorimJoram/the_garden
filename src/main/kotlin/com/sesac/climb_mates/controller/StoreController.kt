package com.sesac.climb_mates.controller

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
        val storeTimeData = storeService.getStoreTimeByStoreId(storeId).get()

        model.addAttribute("storeData", storeData)
        model.addAttribute("menuData", menuData)
        model.addAttribute("storeTimeData", storeTimeData)

        return "store/info"
    }
}