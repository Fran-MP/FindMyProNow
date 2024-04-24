package com.findmypronow.security.hashing

import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.security.SecureRandom

class HashingService_SHA256: HashingService {

    override fun generateSecurityHash(value: String, saltLength: Int): SecurityHash {
        val salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(saltLength)
        val saltAsHex = Hex.encodeHexString(salt)
        val hash = DigestUtils.sha256Hex("$salt$value")
        return SecurityHash(
            hash = hash,
            salt = saltAsHex
        )
    }

    override fun verifyHash(value: String, securityHash: SecurityHash): Boolean {
        return DigestUtils.sha256Hex(securityHash.salt + value) == securityHash.hash
    }
}