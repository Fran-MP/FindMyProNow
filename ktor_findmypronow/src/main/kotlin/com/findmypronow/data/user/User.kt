package com.findmypronow.data.user

import kotlinx.serialization.Serializable
import org.bson.Document
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


@Serializable
data class User(
    @BsonId
    var id: String = ObjectId().toString(),
    var username: String,
    //val name: String?,
    //val lastName: String?,
    //val phone: String?,
    //val email: String?,
    //val address: String?,
    val password: String,
    //val role: String?,
    //val job: String?,
    //val jobDescription: String?,
    val salt: String
) {
    /*companion object {
        fun fromDocument(document: Document): User {
            return User(
                id = document.getObjectId("_id").toString(),
                username = document.getString("username"),
                name = document.getString("name") ?: "",
                lastName = document.getString("lastName") ?: "",
                phone = document.getString("phone") ?: "",
                email = document.getString("email") ?: "",
                address = document.getString("address") ?: "",
                password = document.getString("password") ?: "",
                role = document.getString("role") ?: "",
                job = document.getString("job") ?: "",
                jobDescription = document.getString("jobDescription") ?: "",
                salt = document.getString("stan") ?: ""
            )

        }
    }*/
}