package ru.appvelox.chatsandbox.pagedchat

import android.content.Context
import android.util.Log
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import java.util.*
import kotlin.random.Random


class MessageDataSource(val context: Context): PageKeyedDataSource<Long, Message>(){
    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Message>) {
        Log.d("my tag", "loadInitial ${params.requestedLoadSize}")
        callback.onResult(MessageGenerator.getMessagesRange(0, params.requestedLoadSize), 0, 1)
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Message>) {
        Log.d("my tag", "loadAfter ${params.requestedLoadSize}, ${params.key}")
        val from = params.key.toInt() * params.requestedLoadSize
        val to = from + params.requestedLoadSize
        callback.onResult(MessageGenerator.getMessagesRange(from, to),params.key + 1)
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Message>) {
        Log.d("my tag", "loadBefore ${params.requestedLoadSize}, ${params.key}")
//        callback.onResult(MessageGenerator.getRandomMessageList(params.requestedLoadSize),params.key - 1)
    }

    companion object {
        val TAG = MessageDataSource::class.java.simpleName
    }
}

object MessageGenerator {

    val messages = LinkedList<Message>()

    fun getMessagesRange(from: Int, to: Int): List<Message> {
        if(from > messages.size)
            return listOf()

        if(to < messages.size)
            return messages.subList(from, to)

        return messages.subList(from, messages.size)
    }

    fun addMessage(message: Message){
        messages.add(message)
    }

    private var counter = 1
    val user1 = object : Author {
        override fun getId() = 202

        override fun getName() = "Jason"
    }
    val user2 = object : Author {
        override fun getId() = 670

        override fun getName() = "Garret"
    }

    init {
        messages.addAll(getRandomMessageList(100))
    }

    fun getRandomMessage(): Message {
        return object: Message {
            override fun getId(): Int {
                return Random.nextInt(10000)
            }

            private val mText = "I'll find you in ${counter++} seconds"
            private val mAuthor = if(Random.nextBoolean()) user1 else user2

            override fun getText(): String {
                return mText
            }

            override fun getAuthor(): Author {
                return mAuthor
            }
        }
    }

    fun getRandomMessageList(number: Int): List<Message> {
        val messageList = mutableListOf<Message>()
        for (counter in 0..number)
            messageList.add(getRandomMessage())
        return messageList
    }

}