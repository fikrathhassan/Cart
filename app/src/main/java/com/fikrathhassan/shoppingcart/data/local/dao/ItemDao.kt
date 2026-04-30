package com.fikrathhassan.shoppingcart.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.fikrathhassan.shoppingcart.data.local.entity.CartItemEntity
import com.fikrathhassan.shoppingcart.domain.model.CartTotals
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM cart_items ORDER BY itemName ASC")
    fun getCartItemList(): Flow<List<CartItemEntity>>

    @Query(
        """
    SELECT 
        SUM(sellingPrice * quantity) AS subtotal,
        SUM(sellingPrice * quantity * taxPercentage/100) AS totalTax,
        SUM((sellingPrice * quantity) + 
            (sellingPrice * quantity * taxPercentage/100)) AS total
    FROM cart_items
"""
    )
    fun getCartTotals(): Flow<CartTotals>

    @Query("SELECT * FROM cart_items WHERE itemID = :itemId")
    fun getItemById(itemId: String): CartItemEntity?

    @Upsert
    suspend fun insertItem(item: CartItemEntity)

    @Update
    suspend fun updateItem(item: CartItemEntity)

    @Delete
    suspend fun deleteItem(item: CartItemEntity)

}