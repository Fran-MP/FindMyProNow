import com.findmypronow.data.user.Message
import com.findmypronow.data.user.MessageDataSource
import com.mongodb.internal.client.model.FindOptions
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.toList
import org.bson.Document


class MessageDataSourceImpl(
    private val database: MongoDatabase
) : MessageDataSource {

    private val messages = database.getCollection<Message>("messages")

    override suspend fun getAllMessages(): List<Message> {
        val collection = database.getCollection<Message>("messages")
        val findOptions = FindOptions().sort(Document("timestamp", -1))
        return collection.find().toList()
    }

    override suspend fun insertMessage(message: Message) {
        messages.insertOne(message)
    }
}