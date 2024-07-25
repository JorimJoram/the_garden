package com.sesac.climb_mates.data.store

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="STORE")
data class Store(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name="name", nullable = false)
    var name:String,
    @Column(name = "location", nullable = true)
    val location:String?=null,
    @Column(name="attr", nullable = false)
    var attr:String,
    @Column(name = "lat", nullable = false)
    val lat:String,
    @Column(name = "lon", nullable = false)
    val lon:String,
    @Column(name = "style", nullable = true)
    val style:String, //어떤 유형의 가게인지 확인 -> type은 내장함수일 가능성이 높아서 style로 명명
    @Column(name="is_zero", nullable = true)
    val isZero:Int? = 0,
    @Column(name="is_support", nullable = true)
    val isSupport:Int? = 0,
    @Column(name = "created_date", nullable = false)
    val createdDate: LocalDateTime = LocalDateTime.now()
)
