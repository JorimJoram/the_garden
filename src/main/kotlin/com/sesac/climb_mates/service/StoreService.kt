package com.sesac.climb_mates.service

import com.sesac.climb_mates.data.store.*
import com.sesac.climb_mates.data.store.img.StoreImage
import com.sesac.climb_mates.data.store.img.StoreImageRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrElse

@Service
class StoreService(
    private val storeRepository: StoreRepository,
    private val menuRepository: MenuRepository,
    private val storeTimeRepository: StoreTimeRepository,
    private val storeImageRepository: StoreImageRepository
) {
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

    fun getStoreByName(storeName:String): Store {
        return storeRepository.findByName(storeName).getOrElse {
            Store(
                id=-1,
                name="",
                location = "",
                attr = "",
                lat="", lon = "",
                style = "",
            )
        }
    }

    fun getMenuByStoreId(storeId: Long): List<Menu> {
        return menuRepository.findByStoreId(storeId)
    }

    fun getStoreTimeByStoreId(storeId:Long): Optional<StoreTime> {
        return storeTimeRepository.findByStoreId(storeId)
    }

    fun getStoreImageByStoreId(storeId: Long): Optional<StoreImage> {
        return storeImageRepository.findByStoreId(storeId)
    }

    fun createStore(store:Store): Store {
        return storeRepository.save(store)
    }

    fun createMenu(menu:Menu): Menu {
        return menuRepository.save(menu)
    }

    fun createStoreTime(storeTime: StoreTime): StoreTime {
        return storeTimeRepository.save(storeTime)
    }

    fun createStoreImage(storeImage:StoreImage): StoreImage {
        return storeImageRepository.save(storeImage)
    }
}