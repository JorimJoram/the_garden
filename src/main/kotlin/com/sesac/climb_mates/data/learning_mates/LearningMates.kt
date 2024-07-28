package com.sesac.climb_mates.data.learning_mates

import com.sesac.climb_mates.data.account.Account
import jakarta.persistence.*
import java.time.LocalDateTime
import kotlin.jvm.Transient

@Entity
@Table(name="learning_mates")
data class LearningMates(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    var account: Account,

    @Column(name="applicant_cnt", nullable = false)
    val applicantCnt:Int,
    @Column(name="style", nullable = false)
    val style:String,
    @Column(name="freq", nullable = false)
    val freq:String,
    @Column(name="title", nullable = false)
    val title:String,
    @Column(name="content", nullable = false)
    val content:String,

    @Column(name="created_date", nullable = false)
    val createdDate: LocalDateTime = LocalDateTime.now()
){
    @Transient
    var formattedDate:String? = null;
}