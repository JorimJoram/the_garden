package com.sesac.climb_mates.data.store

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "menu")
data class Menu(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    var store:Store,

    @Column(name="menu", nullable = false)
    var name:String,
    @Column(name = "price", nullable = false)
    var price:Int,
    @Column(name= "url_path", length = 512)
    var urlPath:String? = null,

    @Column(name="created_date", nullable = false)
    val createdDate: LocalDateTime = LocalDateTime.now()
)