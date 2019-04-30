package ru.appvelox.chatsandbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_message.*
import ru.appvelox.chatsandbox.pagedchat.MessageGenerator
import ru.appvelox.chatsandbox.pagedchat.MessageGenerator.user1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chat.setUser(user1)

        button.setOnClickListener {
            chat.addMessage(MessageGenerator.getRandomMessage())
        }
    }
}
