package com.sesac.climb_mates.service

import com.sesac.climb_mates.data.persona.Persona
import com.sesac.climb_mates.data.persona.PersonaRepository
import org.springframework.stereotype.Service

@Service
class PersonaService(
    private val personaRepository: PersonaRepository
) {
    fun getPersonaByStoreId(storeId:Long): Persona {
        return personaRepository.findByStoreId(storeId).get()
    }

    fun createPersona(persona: Persona): Persona {
        return personaRepository.save(persona)
    }
}