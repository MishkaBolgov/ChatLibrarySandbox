package ru.appvelox.chatsandbox.pagedchat

import androidx.recyclerview.widget.DiffUtil
import kotlin.random.Random


interface Message {
    fun getId(): Int
    fun getText(): String
    fun getAuthor(): Author

    companion object{
        val diffCallback = object: DiffUtil.ItemCallback<Message>(){
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem.getId() == newItem.getId()
            }

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem.getText() == newItem.getText()
            }
        }
    }

}

interface Author {
    fun getId(): Int
    fun getName(): String
}
