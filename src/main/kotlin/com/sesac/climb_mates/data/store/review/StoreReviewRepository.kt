package com.sesac.climb_mates.data.store.review

import org.springframework.data.jpa.repository.JpaRepository

interface StoreReviewRepository:JpaRepository<StoreReview, Long> {
    fun findByStoreId(storeId:Long): List<StoreReview>
}