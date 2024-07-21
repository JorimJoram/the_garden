package com.sesac.climb_mates.data.store.time

import com.sesac.climb_mates.data.store.Store
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="store_time")
data class StoreTime(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id:Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    var store: Store,

    @Column(name="start_time")
    var startTime:String? = null,
    @Column(name="end_time")
    var endTime:String? = null,

    @Column(name="break_start")
    var breakStart:String? = null,
    @Column(name="break_end")
    var breakEnd:String? = null,

    @Column(name="dow")//요일
    var DoW:String? = null,

    @Column(name="created_date", nullable = false)
    val createdDate: LocalDateTime = LocalDateTime.now()
)