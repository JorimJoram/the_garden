package com.sesac.climb_mates.data.test

data class GoogleRequest(
    val clientId:String,
    val redirectUri:String,
    val clientSecret:String,
    val responseType:String,

    val scope :String,
    val code:String,
    val accessType:String,

    val grantType:String,
    val state:String? = "",
    val includeGrantedScopes:String? = "",

    val loginHint:String? = "",
    val prompt:String? = ""
)
