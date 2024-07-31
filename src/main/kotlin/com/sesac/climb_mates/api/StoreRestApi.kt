package com.sesac.climb_mates.api

import com.sesac.climb_mates.data.store.Store
import com.sesac.climb_mates.data.store.review.StoreReview
import com.sesac.climb_mates.data.store.review.StoreReviewDTO
import com.sesac.climb_mates.service.StoreService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/store/api")
class StoreRestApi(
    private val storeService: StoreService
) {
    @GetMapping("/list")
    fun getListsByStyle(@RequestParam(name = "style", defaultValue = "default")style:String): List<Store> {
        return storeService.getStoreByStyle(style)
    }

    @GetMapping("/review/list")
    fun getReviewList(@RequestParam(name="store_id")storeId:Long): List<StoreReview> {
        return storeService.getStoreReviewByStoreId(storeId)
    }

    @PostMapping("/review/create")
    fun createReview(@AuthenticationPrincipal user:User, @RequestBody review: StoreReviewDTO): Long {
        return storeService.createReview(review, user).id!!
    }

    @DeleteMapping("/review/del/{reviewId}")
    fun deleteReview(@PathVariable(name="reviewId")reviewId:Long){
        return storeService.deleteReview(reviewId);
    }
}