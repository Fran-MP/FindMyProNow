package com.findmypronow.data


import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase



fun getDatabase(): MongoDatabase {
    val client = MongoClient.create(connectionString = System.getenv("MONGO_LOCAL"))
    return client.getDatabase(databaseName = "FindMyProDB")
}