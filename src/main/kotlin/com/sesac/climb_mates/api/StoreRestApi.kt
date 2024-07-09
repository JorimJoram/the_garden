package com.sesac.climb_mates.api

import com.sesac.climb_mates.data.store.Store
import com.sesac.climb_mates.service.StoreService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/store/api")
class StoreRestApi(
    private val storeService: StoreService
) {
    @GetMapping("/list")
    fun getListsByStyle(@RequestParam(name = "style", defaultValue = "default")style:String): List<Store> {
        return storeService.getStoreByStyle(style)
    }
}