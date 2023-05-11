package com.example.inventorymanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class DonorDashboardActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donor_dashboard)


        val addItemButton = findViewById<Button>(R.id.addItemButton)

        addItemButton.setOnClickListener{
            val intent = Intent(this, AddInventoryItemActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}