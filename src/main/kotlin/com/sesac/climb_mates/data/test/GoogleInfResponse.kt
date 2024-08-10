package com.sesac.climb_mates.data.test


data class GoogleInfResponse(
    val iss:String,
    val azp:String,
    val aud:String,
    val sub:String,
    val email:String,
    val email_verified:String,
    val at_hash:String,
    val name:String,
    val picture:String,
    val given_name:String,
    val family_name:String,
    val locale:String?="",
    val iat:String,
    val exp:String,
    val alg:String,
    val kid:String,
    val typ:String
)
