package com.findmypronow.data.user

import org.bson.Document

interface MessageDataSource {
    suspend fun getAllMessages(): List<Message>
    suspend fun insertMessage(message: Message)
}