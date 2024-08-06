package com.sesac.climb_mates.data.persona

import com.sesac.climb_mates.data.store.Store
import jakarta.persistence.*
import org.apache.logging.log4j.util.StringMap
import java.time.LocalDateTime

@Entity
data class Persona(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    var store: Store,

    @Column(name="person")
    var person:String,
    @Column(name="title", length = 512)
    var title:String,
    @Lob
    var content:String,

    @Column(name="created_date", nullable = false)
    val createdDate: LocalDateTime = LocalDateTime.now(),
)