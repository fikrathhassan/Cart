package com.fikrathhassan.shoppingcart.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fikrathhassan.shoppingcart.data.local.dao.ItemDao
import com.fikrathhassan.shoppingcart.data.local.entity.CartItemEntity

@Database(
    entities = [CartItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}
