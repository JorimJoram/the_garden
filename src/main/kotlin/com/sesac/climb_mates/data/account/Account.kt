package com.sesac.climb_mates.data.account

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name="username", nullable = false, unique = true)
    val username:String,
    @Column(name="pw", nullable = false)
    var password:String,
    @Column(name="class", nullable = false)
    val classRoom:String,
    @Column(name="email", nullable = true)
    var email:String,
    @Column(name = "role", nullable = false)
    var role: String? = "USER", //USER / ADMIN으로 구분
    @Column(name="image_path", nullable = true)
    var imagePath:String? = "/img/default/profile.png",
    @Column(name = "created_date", nullable = false)
    val createdDate: LocalDateTime = LocalDateTime.now()
)