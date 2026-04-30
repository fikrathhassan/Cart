package com.fikrathhassan.shoppingcart.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ItemDto(
    val itemID: String?,
    val itemName: String?,
    val sellingPrice: Double?,
    val taxPercentage: Double?
)
