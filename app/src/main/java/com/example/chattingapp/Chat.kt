package com.example.chattingapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.temporal.ValueRange

class Chat: Fragment() {
    private lateinit var chatrecyclerview: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDataBase: DatabaseReference

    var receiver: String? = null
    var sender: String? = null


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        val name = arguments?.getString("name")
        val receiverUid = arguments?.getString("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        mDataBase = FirebaseDatabase.getInstance().getReference()

        sender = receiverUid + senderUid
        receiver = senderUid + receiverUid


        (requireActivity() as AppCompatActivity).supportActionBar?.title = name

        chatrecyclerview = view.findViewById<RecyclerView>(R.id.chatRecyclerView)
        messageBox = view.findViewById(R.id.messageBox)
        sendButton = view.findViewById(R.id.sentButton)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(requireContext(), messageList)

        chatrecyclerview.layoutManager = LinearLayoutManager(requireContext())
        chatrecyclerview.adapter = messageAdapter

        // adding data to recyclerView
        mDataBase.child("chats").child(sender!!).child("messages")
            .addValueEventListener(object: ValueEventListener{
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()

                    for (postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })


        sendButton.setOnClickListener {
            val message = messageBox.text.toString()
            val messageObject = Message(message, senderUid)

            mDataBase.child("chats").child(sender!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDataBase.child("chats").child(receiver!!).child("messages").push()
                        .setValue(messageObject)

                }
            messageBox.setText("")

        }



        return view
    }
}