package com.fikrathhassan.shoppingcart.di

import android.content.Context
import androidx.room.Room
import com.fikrathhassan.shoppingcart.data.local.database.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): Database = Room.databaseBuilder(context, Database::class.java, "shopping_cart").build()

    @Singleton
    @Provides
    fun provideItemDao(database: Database) = database.itemDao()
}