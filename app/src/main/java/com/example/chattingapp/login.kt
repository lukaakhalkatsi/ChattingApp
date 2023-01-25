package com.example.chattingapp

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class login: Fragment() {
    private lateinit var loginButton: Button
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val text : TextView = view.findViewById(R.id.forgotPassword)
        val reg : TextView = view.findViewById(R.id.goToRegister)

        text.setOnClickListener {
            val fragment = resetPassword()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.layout1, fragment)?.addToBackStack(null)?.commit()

        }

        reg.setOnClickListener {
            val fragment = register()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.layout1, fragment)?.addToBackStack(null)?.commit()

        }

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        editEmail = view.findViewById(R.id.inputEmail)
        editPassword = view.findViewById(R.id.inputPassword)
        loginButton = view.findViewById(R.id.btnLogin)

        loginButton.setOnClickListener {
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()
            if (email.isNotEmpty() || email.isNotEmpty() || password.isNotEmpty()){
                login(email, password)
            }else {
                Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
            }



        }

    }

    private fun login(email : String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "you're logged", Toast.LENGTH_SHORT).show()
                    fragmentManager?.beginTransaction()
                        ?.replace(R.id.layout1, recyclerview())
                        ?.commit()
                } else {
                    Toast.makeText(requireContext(), "theres some error", Toast.LENGTH_SHORT).show()
                }
            }

    }


}