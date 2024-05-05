package com.findmypronow.data.model

interface MessageDataSource {
    suspend fun getAllMessages(): List<Message>
    suspend fun insertMessage(message: Message)
}