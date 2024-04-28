package com.findmypronow.di

import MessageDataSourceImpl
import com.findmypronow.data.getDatabase
import com.findmypronow.data.user.MessageDataSource
import com.findmypronow.room.RoomController
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoCollection
import org.koin.dsl.module

val mainModule = module {
    single{
        MongoClient.create(connectionString = System.getenv("MONGO_URI")).getDatabase(databaseName = "FindMyProDB")
    }
    single<MessageDataSource> {
        MessageDataSourceImpl(get())
    }
    single {
        RoomController(get())
    }
}