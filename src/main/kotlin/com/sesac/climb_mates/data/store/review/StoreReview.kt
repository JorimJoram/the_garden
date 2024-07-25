package com.sesac.climb_mates.data.store.review

import com.sesac.climb_mates.data.account.Account
import com.sesac.climb_mates.data.store.Store
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="store_review")
data class StoreReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    var store: Store,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    var account: Account,

    @Column(name="content", nullable = false)
    var content:String,
    @Column(name="star", nullable = false)
    var star:Int,

    @Column(name="created_date", nullable = false)
    val createdDate: LocalDateTime = LocalDateTime.now()
)
