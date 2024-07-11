package com.sesac.climb_mates.data.article

import com.sesac.climb_mates.data.account.Account
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name="article")
data class Article(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id:Long? = null,
    @Column(name="title", nullable = false)
    var title:String,
    @Column(name = "content", nullable = false, length = 50000)
    var content:String,
    @Column(name="view", nullable = false)
    var view:Int? = 0,
    @Column(name="love", nullable = false)
    var love:Int? = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_id", nullable = false)
    var account: Account,
    @Column(name="created_date", nullable = false)
    val createdDate:LocalDateTime = LocalDateTime.now()
    //test
)
