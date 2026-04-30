package com.fikrathhassan.shoppingcart.domain.datasource

import com.fikrathhassan.shoppingcart.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemDataSource {

    fun getItemList(
        onStart: () -> Unit,
        onComplete: () -> Unit
    ): Flow<List<Item>>

}