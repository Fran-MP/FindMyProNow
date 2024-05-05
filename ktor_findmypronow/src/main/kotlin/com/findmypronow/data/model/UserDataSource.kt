package com.findmypronow.data.model

interface UserDataSource {

    suspend fun getUserInfo(userId: String): User?
    suspend fun getUserByUserName(username: String): User?
    suspend fun insertUser(user: User): Boolean
    suspend fun deleteUser(userId: String): Boolean
    suspend fun updateUserInfo(
        userId: String,
        firstName: String,
        lastName: String
    ): Boolean
}