package com.marik.firebasestoragefirst.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.marik.firebasestoragefirst.R
import kotlinx.android.synthetic.main.login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

   companion object {
        lateinit var firebaseAuth: FirebaseAuth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            finish()
            // call profile activity
            startActivity(Intent(this, MainActivity::class.java))
        }

        buttonSignIn.setOnClickListener(this)
        textViewSignUp.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v) {
            buttonSignIn -> userLogin()
            textViewSignUp -> {
                finish()
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }

    private fun userLogin() {
        val email = editTextEmailLogin.text.toString()
        val password = editTextPasswordLogin.text.toString()

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
        progress_login.visibility = View.VISIBLE
        layout_login.visibility = View.GONE

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                progress_login.visibility = View.GONE
                if (task.isSuccessful) {
                    finish()
                    // user successfully registered and logged in
                    // we will start the profile activity here
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Could not sign in. Please try again.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}