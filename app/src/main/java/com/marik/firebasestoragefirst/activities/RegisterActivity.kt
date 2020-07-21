package com.marik.firebasestoragefirst.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.marik.firebasestoragefirst.R
import kotlinx.android.synthetic.main.register.*

class RegisterActivity : AppCompatActivity() {
   companion object {
        lateinit var firebaseAuth: FirebaseAuth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            finish()
            // call profile activity
            startActivity(Intent(this, MainActivity::class.java))
        }

        setListeners()
    }

    private fun setListeners() {
        buttonRegister.setOnClickListener { registerUser() }
        textViewSignin.setOnClickListener {
            startActivity(
                Intent(
                    this@RegisterActivity,
                    LoginActivity::class.java
                )
            )
        }
    }

    private fun registerUser() {
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()

        if (email.isEmpty() || !email.contains('@')) {
            // email is empty
            Toast.makeText(this, "Please enter valid email", Toast.LENGTH_LONG).show()
            // stopping the function execution
            return
        }

        if (password.isEmpty()) {
            // password is empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show()
            // stopping the function execution
            return
        }

        // if email and password are valid, we will show progress bar
        progress_register.visibility = View.VISIBLE
        layout_register.visibility = View.GONE

        // register or sign in user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    finish()
                    // user successfully registered and logged in
                    // we will start the profile activity here
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Could not register. Please try again.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}