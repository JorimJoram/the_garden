package com.sesac.climb_mates.data.campus

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="campus")
data class Campus(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null,

    @Column(name="name", nullable = false)
    var name:String,
    @Column(name="location", nullable = false)
    var location:String,

    @Column(name="created_date", nullable = false)
    val createdDate: LocalDateTime = LocalDateTime.now(),
)
