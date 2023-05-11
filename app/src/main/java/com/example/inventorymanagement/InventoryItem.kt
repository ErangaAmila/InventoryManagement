package com.example.inventorymanagement

data class InventoryItem(
    val userId: String,
    val name: String,
    val address: String,
    val phoneNumber: String,
    val quantity: Int,
    val expireDate: String
)
