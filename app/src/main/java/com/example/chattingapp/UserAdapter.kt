package com.example.chattingapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.core.Context

class UserAdapter(val context: android.content.Context, val userList: ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.fragment_user, parent, false)
        return UserViewHolder(view)
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.txtName.text = currentUser.name

        holder.itemView.setOnClickListener {
            val fragmentManager = (context as FragmentActivity).supportFragmentManager
            val bundle = Bundle()
            bundle.putString("name", currentUser.name)
            bundle.putString("uid", currentUser.uid)
            val chatFragment = Chat()
            chatFragment.arguments = bundle
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.layout1, chatFragment)
            transaction.addToBackStack(null)
            transaction.commit()


        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtName = itemView.findViewById<TextView>(R.id.txt_name)
    }
}