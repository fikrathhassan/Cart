package com.fikrathhassan.shoppingcart.data.mapper

import com.fikrathhassan.shoppingcart.data.local.entity.CartItemEntity
import com.fikrathhassan.shoppingcart.data.remote.dto.ItemDto
import com.fikrathhassan.shoppingcart.domain.model.Item
import com.fikrathhassan.shoppingcart.orZero

fun ItemDto.toItem(): Item {
    return Item(
        id = itemID.orEmpty(),
        name = itemName.orEmpty(),
        sellingPrice = sellingPrice.orZero(),
        taxPercentage = taxPercentage.orZero(),
        quantity = 0
    )
}

fun Item.toCartItemEntity(): CartItemEntity {
    return CartItemEntity(
        itemID = id,
        itemName = name,
        sellingPrice = sellingPrice,
        taxPercentage = taxPercentage,
        quantity = quantity
    )
}

fun CartItemEntity.toItem(): Item {
    return Item(
        id = itemID,
        name = itemName,
        sellingPrice = sellingPrice,
        taxPercentage = taxPercentage,
        quantity = quantity
    )
}