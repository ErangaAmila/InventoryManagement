package com.example.inventorymanagement
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnSignUp.setOnClickListener {
            // Create an AlertDialog Builder
            val builder = AlertDialog.Builder(this)

            // Set the dialog properties
            builder.setTitle("Choose an option")

            // Set up the buttons
            builder.setPositiveButton("Donator Sign Up") { dialog, which ->
                val intent = Intent(this@MainActivity, DonatorSignUpActivity::class.java)
                startActivity(intent)
            }

            builder.setNegativeButton("Volunteer Sign Up") { dialog, which ->
                // TODO: Navigate to your Volunteer Sign Up activity
            }

            // Create and show the dialog
            builder.create().show()
        }


        btnLogin.setOnClickListener {
            // TODO: Navigate to your Login activity
            btnLogin.setOnClickListener {
                val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
                startActivity(intent)
            }


        }
    }
}