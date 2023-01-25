package com.example.chattingapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class resetPassword: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_resetpass, container, false)
        val rstPassword: EditText = view.findViewById(R.id.editTextTextPersonName)
        val rstButton : Button = view.findViewById(R.id.button)
        val mAuth: FirebaseAuth

        mAuth = FirebaseAuth.getInstance()
        rstButton.setOnClickListener {
            val rsttPassword= rstPassword.text.toString()

            if (rsttPassword.isNotEmpty()){
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Are You Sure to Change Your Pass?")
                builder.setPositiveButton("Confirm") { dialog, which ->
                    val sPassword = rstPassword.text.toString()
                    mAuth.sendPasswordResetEmail(sPassword)
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "Check your email", Toast.LENGTH_SHORT).show()
                            fragmentManager?.beginTransaction()
                                ?.replace(R.id.layout1, login())
                                ?.commit()
                        }
                        .addOnFailureListener {
                            Toast.makeText(requireContext(), "theres some error", Toast.LENGTH_SHORT).show()
                        }
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()


            }else {
                Toast.makeText(requireContext(), "Empty", Toast.LENGTH_SHORT).show()
            }
            }



        return view
    }

}


