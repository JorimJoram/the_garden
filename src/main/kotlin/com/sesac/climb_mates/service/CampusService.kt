package com.sesac.climb_mates.service

import com.sesac.climb_mates.data.campus.Campus
import com.sesac.climb_mates.data.campus.CampusClass
import com.sesac.climb_mates.data.campus.CampusClassRepository
import com.sesac.climb_mates.data.campus.CampusRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CampusService(
    private val campusRepository: CampusRepository,
    private val campusClassRepository: CampusClassRepository
) {
    fun getCampusClassListByCampus(campus:String): List<CampusClass> {
        return campusClassRepository.findByCampusName(campus)
    }

    fun getCampusByName(campusName:String): Optional<Campus> {
        return campusRepository.findByName(campusName)
    }

    fun createCampus(campus:String, location:String): Campus {
        return campusRepository.save(
            Campus(
                name = campus,
                location = location
            )
        )
    }

    fun createCampusClass(campusClass:CampusClass): CampusClass {
        return campusClassRepository.save(campusClass)
    }

    fun deleteById(id: Long) {
        campusRepository.deleteById(id)
    }
}