package com.example.chattingapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class ThirdScreen: Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_thirdscreen, container, false)
        val butt = view.findViewById<Button>(R.id.button3)

        butt.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.layout1, login())
                ?.commit()
        }

        return view
    }
}