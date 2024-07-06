package com.sesac.climb_mates.service

import com.sesac.climb_mates.data.store.Store
import com.sesac.climb_mates.data.store.StoreRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class StoreService(
    private val storeRepository: StoreRepository
) {
    fun createStore(store:Store): Store {
        return storeRepository.save(store)
    }

    fun getAllStore(): List<Store> {
        return storeRepository.findAll()
    }

    fun getStoreByStyle(style: String): List<Store> {
        return if (style=="default"){
            storeRepository.findAll()
        }else{
            storeRepository.findByStyle(style).get()
        }
    }
}