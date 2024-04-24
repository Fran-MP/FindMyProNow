package com.findmypronow.data.user


import kotlinx.coroutines.flow.firstOrNull
import org.bson.Document


class MongoUserDataSource(database: com.mongodb.kotlin.client.coroutine.MongoDatabase): UserDataSource {


    private val users = database.getCollection<User>("User")

    override suspend fun getUserByUserName(username: String): User? {
        val query = Document("username", username)
        val result = users.find(query).firstOrNull()
        return result
    }

    override suspend fun insertUser(user: User): Boolean {
        return users.insertOne(user).wasAcknowledged()
    }
}

