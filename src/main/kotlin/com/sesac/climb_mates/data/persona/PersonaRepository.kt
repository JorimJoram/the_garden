package com.sesac.climb_mates.data.persona

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface PersonaRepository:JpaRepository<Persona, Long> {
    fun findByStoreId(storeId:Long):Optional<Persona>
}