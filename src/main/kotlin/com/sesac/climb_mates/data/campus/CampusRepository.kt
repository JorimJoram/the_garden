package com.sesac.climb_mates.data.campus

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CampusRepository:JpaRepository<Campus, Long> {
    fun findByName(name:String):Optional<Campus>
}