package com.sesac.climb_mates.data.log

import com.sesac.climb_mates.data.account.Account
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="filter_data")
data class FilterData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    var account: Account,

    @Column(name="style", nullable = false)
    val style:String,

    @Column(name="created_date", nullable = false)
    val createdDate: LocalDateTime = LocalDateTime.now()
)
