package com.sesac.climb_mates.data.store.img

import com.sesac.climb_mates.data.store.Store
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="store_image")
data class StoreImage(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id:Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    var store: Store,

    @Column(name="path", nullable = false, length = 512)
    var path:String,

    @Column(name="size", nullable = true)
    var size:Int,

    @Column(name="created_date", nullable = false)
    val createdDate: LocalDateTime = LocalDateTime.now()
)
