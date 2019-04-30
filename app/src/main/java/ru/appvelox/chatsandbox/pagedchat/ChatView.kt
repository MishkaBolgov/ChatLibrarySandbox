package ru.appvelox.chatsandbox.pagedchat

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ChatView(context: Context, attributeSet: AttributeSet) : RecyclerView(context, attributeSet) {
    var messageAdapter: MessageAdapter = MessageAdapter()
    val messageDataSource = MessageDataSource(context)
    val messageList = getPagedMessageList()

    init {
        layoutManager = LinearLayoutManager(context)
        messageAdapter.submitList(messageList)
        adapter = messageAdapter
    }

    fun setUser(user: Author){
        messageAdapter.user = user
    }

    fun setOnLoadMessagesRequestListener(onLoadMessagesRequestListener: OnLoadMessagesRequestListener){

    }

    private fun getPagedMessageList(): PagedList<Message> {
        val executor = Executors.newFixedThreadPool(5)
        val executor2 = object:  Executor {
            val handler = Handler(Looper.getMainLooper())
            override fun execute(command: Runnable?) {
                handler.post(command)
            }
        }

        val configs = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(10)
            .setPageSize(20)
            .build()

        return PagedList.Builder<Long, Message>(messageDataSource, configs)
            .setFetchExecutor(executor)
            .setNotifyExecutor(executor2)
            .build()
    }

    fun addMessage(message: Message) {
//        MessageGenerator.addMessage(message)
    }

    interface OnLoadMessagesRequestListener {

    }
}
