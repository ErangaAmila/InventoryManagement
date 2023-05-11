package com.example.inventorymanagement

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddInventoryItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_inventory_item)

        val itemName = findViewById<EditText>(R.id.itemName)
        val itemAddress = findViewById<EditText>(R.id.itemAddress)
        val itemPhoneNumber = findViewById<EditText>(R.id.itemPhoneNumber)
        val itemQuantity = findViewById<EditText>(R.id.itemQuantity)
        val itemExpireDate = findViewById<EditText>(R.id.itemExpireDate)
        val itemImage = findViewById<ImageView>(R.id.itemImage)

        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            val itemNameValue = itemName.text.toString()
            val itemAddressValue = itemAddress.text.toString()
            val itemPhoneNumberValue = itemPhoneNumber.text.toString()
            val itemQuantityValue = itemQuantity.text.toString().toIntOrNull() // Convert to Int, or null if not a valid number
            val itemExpireDateValue = itemExpireDate.text.toString()

            if (itemNameValue.isEmpty() || itemAddressValue.isEmpty() || itemPhoneNumberValue.isEmpty() ||
                itemQuantityValue == null || itemExpireDateValue.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save data in Firebase Realtime Database
            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
            val database = FirebaseDatabase.getInstance().getReference("InventoryItems")

            val key = database.push().key // Generate a new unique key for the item
            if (key == null) {
                Log.w(TAG, "Could not get a key for the item")
                return@setOnClickListener
            }

            val item = InventoryItem(userId, itemNameValue, itemAddressValue, itemPhoneNumberValue, itemQuantityValue, itemExpireDateValue)

            database.child(key).setValue(item).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Item saved successfully", Toast.LENGTH_SHORT).show()
                    // TODO: Navigate back to the DonorDashboardActivity or clear the input fields
                    // Navigate back to the DonorDashboardActivity
                    val intent = Intent(this, DonorDashboardActivity::class.java)
                    startActivity(intent)
                    finish() // This will finish the current activity and prevent going back to it from the DonorDashboardActivity
                } else {
                    Log.w(TAG, "saveItem:failure", task.exception)
                    Toast.makeText(baseContext, "Save failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
