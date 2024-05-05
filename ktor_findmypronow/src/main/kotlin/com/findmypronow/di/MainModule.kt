package com.findmypronow.di

import MessageDataSourceImpl
import com.findmypronow.data.model.MessageDataSource
import com.findmypronow.room.RoomController
import com.mongodb.kotlin.client.coroutine.MongoClient
import org.koin.dsl.module

val mainModule = module {
    single{
        MongoClient.create(connectionString = System.getenv("MONGO_LOCAL")).getDatabase(databaseName = "FindMyProDB")
    }
    single<MessageDataSource> {
        MessageDataSourceImpl(get())
    }
    single {
        RoomController(get())
    }
}