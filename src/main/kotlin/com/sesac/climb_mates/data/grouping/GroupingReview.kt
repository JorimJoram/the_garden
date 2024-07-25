package com.sesac.climb_mates.data.grouping

import com.sesac.climb_mates.data.account.Account
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="grouping_review")
data class GroupingReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grouping_id", nullable = false)
    var grouping: Grouping,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    var account: Account,

    @Column(name="content", nullable = false)
    var content:String,

    @Column(name="created_date", nullable = false)
    val createdDate: LocalDateTime = LocalDateTime.now()
)