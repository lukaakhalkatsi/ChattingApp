package com.example.chattingapp

import android.os.Bundle
import android.os.health.UidHealthStats
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class register: Fragment() {
    private lateinit var registerButton: Button
    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDataBase: DatabaseReference
    private lateinit var checkBox: CheckBox
    private lateinit var terms: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerButton = view.findViewById(R.id.button2)
        editName = view.findViewById(R.id.editTextTextPersonName2)
        editEmail = view.findViewById(R.id.editTextTextPersonName3)
        editPassword = view.findViewById(R.id.editTextTextPersonName4)
        mAuth = FirebaseAuth.getInstance()
        checkBox = view.findViewById(R.id.checkBox2)
        terms = view.findViewById(R.id.textView5)

        terms.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.layout1, termsconditions())?.addToBackStack(null)?.commit()

        }


        registerButton.setOnClickListener {
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
            }else if(checkBox.isChecked){
                signUp(name,email, password)
            }




        }
    }

    private fun signUp(name: String,email: String, password: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    fragmentManager?.beginTransaction()
                        ?.replace(R.id.layout1, viewpager())
                        ?.commit()
                    addUserToDataBase(name, email, mAuth.currentUser?.uid!!)
                    Toast.makeText(requireContext(), "Successful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addUserToDataBase(name: String, email: String, uid: String){
        mDataBase = FirebaseDatabase.getInstance().getReference()

        mDataBase.child("user").child(uid).setValue(User(name, email, uid))

    }
}


