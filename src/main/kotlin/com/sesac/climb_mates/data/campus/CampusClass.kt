package com.sesac.climb_mates.data.campus

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="campus_class")
data class CampusClass(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id", nullable = false)
    var campus: Campus,

    @Column(name="name", nullable = false)
    var name:String,
    @Column(name="time",  nullable = true)
    var time:Int? = null,

    @Column(name="created_date", nullable = false)
    val createdDate: LocalDateTime = LocalDateTime.now(),
)
