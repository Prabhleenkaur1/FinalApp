package com.example.finalapp


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonRegister: Button
    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize views
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonRegister = findViewById(R.id.buttonRegister)

        buttonLogin = findViewById(R.id.buttonLogin)

        buttonLogin.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }

        buttonRegister.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            // Basic validation for email and password
            if (email.isBlank()) {
                Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.isBlank()) {
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Firebase Authentication to create a user with email and password
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // User is successfully registered and logged in
                        val user = auth.currentUser
                        // Write a message to the database
                        val database = FirebaseDatabase.getInstance()
                        val myRef = database.getReference("users").child(user!!.uid)

                        val userInfo = hashMapOf(
                            "email" to email
                        )

                        myRef.setValue(userInfo).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT)
                                    .show()
                                // Direct the user to the HomePageActivity
                                val intent = Intent(this, Homepage::class.java)
                                startActivity(intent)
                                finish() // Optional: If you don't want them to go back to the registration screen
                            } else {
                                Toast.makeText(
                                    this,
                                    "Failed to register user details",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        // If registration fails, display a message to the user.
                        Toast.makeText(
                            this,
                            "Registration Failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}