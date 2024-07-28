package com.sesac.climb_mates.data.learning_mates

import com.sesac.climb_mates.data.account.Account
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="learning_applicant")
data class LearningApplicant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_mates_id", nullable = false)
    var learningMates: LearningMates,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    var account: Account,

    @Column(name="created_date", nullable = false)
    val createdDate: LocalDateTime = LocalDateTime.now()
)
