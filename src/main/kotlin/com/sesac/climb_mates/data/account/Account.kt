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

    @Column(name="name", nullable = false)
    var name:String,
    @Column(name="nickname", nullable = false, unique = true)
    var nickname:String,
    @Column(name="email", nullable = false, unique = true)
    var email:String,
    @Column(name="tel", nullable = false)
    val tel:String,

    @Column(name="birth", nullable = false)
    val birth:String,
    @Column(name="gender", nullable = false)
    val gender:Int,

    @Column(name="campus", nullable = true)
    val campus:String,
    @Column(name="education", nullable = true)
    val education:String,

    @Column(name = "role", nullable = false)
    var role: String? = "USER", //USER / ADMIN으로 구분
    @Column(name="image_path", nullable = true)
    var imagePath:String? = "/img/default/profile.png",
    @Column(name = "created_date", nullable = false)
    val createdDate: LocalDateTime = LocalDateTime.now()
)