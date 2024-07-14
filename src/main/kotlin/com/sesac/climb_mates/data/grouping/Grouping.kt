package com.sesac.climb_mates.data.grouping

import com.sesac.climb_mates.data.account.Account
import com.sesac.climb_mates.data.store.Store
import jakarta.persistence.*
import java.time.LocalDateTime
import kotlin.jvm.Transient

@Entity
@Table(name="grouping")
data class Grouping(
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    var store: Store,
    @Column(name="created_date", nullable = false)
    val createdDate:LocalDateTime = LocalDateTime.now(),
){
    @Transient
    var formattedDate:String? = null
}
