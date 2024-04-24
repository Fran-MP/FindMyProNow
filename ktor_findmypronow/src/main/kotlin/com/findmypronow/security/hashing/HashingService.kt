package com.findmypronow.security.hashing

interface HashingService {
    fun generateSecurityHash(value: String, saltLength: Int = 32): SecurityHash
    fun verifyHash(value: String, securityHash: SecurityHash): Boolean
}