package com.findmypronow.data.model


import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.client.result.DeleteResult
import kotlinx.coroutines.flow.firstOrNull
import org.bson.Document
import com.mongodb.kotlin.client.coroutine.MongoClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext


class MongoUserDataSourceImpl(database: com.mongodb.kotlin.client.coroutine.MongoDatabase): UserDataSource {


    private val users = database.getCollection<User>("User")

    override suspend fun getUserInfo(userId: String): User? {
        return users.find(Filters.eq("_id", userId)).firstOrNull()
    }

    override suspend fun getUserByUserName(username: String): User? {
        val query = Document("username", username)
        val result = users.find(query).firstOrNull()
        return result
    }

    override suspend fun insertUser(user: User): Boolean {
        return users.insertOne(user).wasAcknowledged()
    }


    override suspend fun deleteUser(userId: String): Boolean = withContext(Dispatchers.IO) {
        val result: DeleteResult = users.deleteOne(Filters.eq("_id", userId))
        result.wasAcknowledged()
    }

    override suspend fun updateUserInfo(userId: String, firstName: String, lastName: String): Boolean =
        withContext(Dispatchers.IO) {
            val updateResult = users.updateOne(
                Filters.eq("_id", userId),
                Updates.set("name", "$firstName $lastName")
            )
            updateResult.wasAcknowledged()
        }
}

