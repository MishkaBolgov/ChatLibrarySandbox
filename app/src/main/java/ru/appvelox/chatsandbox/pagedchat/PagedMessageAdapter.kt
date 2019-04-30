package ru.appvelox.chatsandbox.pagedchat

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_message.view.*
import ru.appvelox.chatsandbox.R

class MessageAdapter: PagedListAdapter<Message, MessageViewHolder>(Message.diffCallback) {
    var user: Author? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)

        when (viewType) {
            MessageAdapter.MessageType.LEFT.type -> view.background = getLeftBackground()
            MessageAdapter.MessageType.RIGHT.type -> view.background = getRightBackground()
        }

        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun getDrawable(): GradientDrawable {
        val drawable = GradientDrawable()
        return drawable
    }

    private fun getLeftBackground(): Drawable {
        val drawable = getDrawable()

        drawable.setColor(Color.parseColor("#19000000"))
        drawable.cornerRadii = floatArrayOf(0f, 0f, 20f, 20f, 20f, 20f, 20f, 20f)

        return drawable
    }

    private fun getRightBackground(): Drawable {
        val drawable = getDrawable()

        drawable.setColor(Color.parseColor("#19008AFF"))
        drawable.cornerRadii = floatArrayOf(20f, 20f, 0f, 0f, 20f, 20f, 20f, 20f)

        return drawable
    }

    override fun getItemViewType(position: Int): Int {
        val messageAuthorId = getItem(position)?.getAuthor()?.getId()
        val userId = user?.getId()
        return if (messageAuthorId == userId) MessageType.RIGHT.type else MessageType.LEFT.type
    }


    enum class MessageType(val type: Int) {
        LEFT(0), RIGHT(1)
    }
}

class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(message: Message?) {
        if(message == null) return
        itemView.message.text = message.getText()
        itemView.author.text = "${message.getAuthor().getName()}:"
    }
}