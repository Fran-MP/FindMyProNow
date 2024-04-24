package com.findmypronow.security.hashing

data class SecurityHash(
    val hash: String,
    val salt: String
)
