package com.sesac.climb_mates.data.campus

import org.springframework.data.jpa.repository.JpaRepository

interface CampusClassRepository:JpaRepository<CampusClass, Long> {
    fun findByCampusName(campusName:String):List<CampusClass>
}