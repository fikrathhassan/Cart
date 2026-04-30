package com.fikrathhassan.shoppingcart.ui.model

import com.fikrathhassan.shoppingcart.domain.model.Item
import com.fikrathhassan.shoppingcart.toRoundedTwoDecimal

data class ItemUi(
    val id: String,
    val name: String,
    val price: PriceFormatter,
    val taxPercentage: Double,
    var quantity: Int = 0
)

data class PriceFormatter(
    val originalPrice: Double,
    val formattedPrice: String
)

fun Double.toPriceFormatter(): PriceFormatter {
    return PriceFormatter(
        originalPrice = this,
        formattedPrice = "Rs. ${this.toRoundedTwoDecimal()}/-"
    )
}

fun ItemUi.toItem(): Item {
    return Item(
        id = id,
        name = name,
        sellingPrice = price.originalPrice,
        taxPercentage = taxPercentage,
        quantity = quantity
    )
}

fun Item.toItemUi(): ItemUi {
    return ItemUi(
        id = id,
        name = name,
        price = sellingPrice.toPriceFormatter(),
        taxPercentage = taxPercentage,
        quantity = quantity
    )
}