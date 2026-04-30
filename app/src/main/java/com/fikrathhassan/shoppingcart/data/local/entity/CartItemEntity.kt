package com.fikrathhassan.shoppingcart.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val itemID: String,
    val itemName: String,
    val sellingPrice: Double,
    val taxPercentage: Double,
    var quantity: Int
)
