package com.sesac.climb_mates.data.learning_mates

import com.sesac.climb_mates.data.account.Account
import com.sesac.climb_mates.data.grouping.Grouping
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="learning_review")
data class LearningReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_id", nullable = false)
    var learningMates: LearningMates,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    var account: Account,

    @Column(name="content", nullable = false)
    var content:String,

    @Column(name="created_date", nullable = false)
    val createdDate: LocalDateTime = LocalDateTime.now()
)