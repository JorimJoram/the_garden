package com.sesac.climb_mates.service

import com.sesac.climb_mates.data.store.Store
import com.sesac.climb_mates.data.store.StoreRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class StoreService(
    private val storeRepository: StoreRepository
) {
    fun createStore(store:Store): Store {
        return storeRepository.save(store)
    }

    fun getStoreByStyle(style: String): List<Store> {
        return if (style=="default"){
            storeRepository.findAll()
        }else{
            storeRepository.findByStyle(style).get()
        }
    }

    fun getStoreById(id:Long): Store {
        return storeRepository.findById(id).get()
    }
}