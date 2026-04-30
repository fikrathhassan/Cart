package com.fikrathhassan.shoppingcart.domain.model

data class Item(
    val id: String,
    val name: String,
    val sellingPrice: Double,
    val taxPercentage: Double,
    val quantity: Int
)