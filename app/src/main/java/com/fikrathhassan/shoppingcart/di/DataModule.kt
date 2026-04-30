package com.fikrathhassan.shoppingcart.di

import com.fikrathhassan.shoppingcart.data.local.CartDataSourceImpl
import com.fikrathhassan.shoppingcart.data.remote.ItemDataSourceImpl
import com.fikrathhassan.shoppingcart.domain.datasource.CartDataSource
import com.fikrathhassan.shoppingcart.domain.datasource.ItemDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindItemDataSource(impl: ItemDataSourceImpl): ItemDataSource

    @Singleton
    @Binds
    abstract fun bindCartDataSource(impl: CartDataSourceImpl): CartDataSource

}