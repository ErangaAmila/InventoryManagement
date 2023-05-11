package com.example.inventorymanagement
import android.content.ContentValues.TAG
import com.google.firebase.database.FirebaseDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent


class DonatorSignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donator_sign_up)

        val firstName = findViewById<EditText>(R.id.firstName)
        val lastName = findViewById<EditText>(R.id.lastName)
        val email = findViewById<EditText>(R.id.email)
        val address = findViewById<EditText>(R.id.address)
        val postalCode = findViewById<EditText>(R.id.postalCode)
        val password = findViewById<EditText>(R.id.password)

        val registerButton = findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {
            val firstNameValue = firstName.text.toString()
            val lastNameValue = lastName.text.toString()
            val emailValue = email.text.toString()
            val addressValue = address.text.toString()
            val postalCodeValue = postalCode.text.toString()
            val passwordValue = password.text.toString()

            if (firstNameValue.isEmpty() || lastNameValue.isEmpty() || emailValue.isEmpty() ||
                addressValue.isEmpty() || postalCodeValue.isEmpty() || passwordValue.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Register donator with Firebase Authentication
            val auth = FirebaseAuth.getInstance()

            auth.createUserWithEmailAndPassword(emailValue, passwordValue)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign up success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser

                        // Create a Donator object
                        val donator = Donator(firstNameValue, lastNameValue, emailValue, addressValue, postalCodeValue)

                        // Get a reference to the database
                        val database = FirebaseDatabase.getInstance()

                        // Get a reference to the "donators" location
                        val donatorRef = database.getReference("donators")

                        // Save the Donator object at the user's UID location within "donators"
                        user?.let {
                            donatorRef.child(it.uid).setValue(donator).addOnSuccessListener {
                                // After the data is saved successfully, show a Toast message
                                Toast.makeText(this, "Successfully registered!", Toast.LENGTH_SHORT).show()

                                // Then navigate to the welcome page
                                val intent = Intent(this, WelcomeActivity::class.java)
                                startActivity(intent)
                                finish() // This will finish the current activity and prevent going back to it from the WelcomeActivity
                            }
                        }
                    } else {
                        // If sign up fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Sign up failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }

            // TODO: Validate input and register donator
        }

    }

}