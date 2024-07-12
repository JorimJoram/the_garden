package com.sesac.climb_mates.data.store.img

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface StoreImageRepository:JpaRepository<StoreImage, Long> {
    fun findByStoreId(storeId: Long): Optional<StoreImage>
}