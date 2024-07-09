package com.sesac.climb_mates.controller

import com.sesac.climb_mates.service.StoreService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

import org.springframework.ui.Model

@Controller
@RequestMapping("/store")
class StoreController(
    private val storeService: StoreService
) {
    @GetMapping("/info")
    fun infoPage(@RequestParam(defaultValue = "none", name="id")id:Long,  model: Model): String {
        val storeData =  storeService.getStoreById(id)
        model.addAttribute("storeData", storeData)
        return "store/info"
    }
}