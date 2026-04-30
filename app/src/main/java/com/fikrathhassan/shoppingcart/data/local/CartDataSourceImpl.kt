package com.fikrathhassan.shoppingcart.data.local

import androidx.annotation.WorkerThread
import com.fikrathhassan.shoppingcart.data.local.dao.ItemDao
import com.fikrathhassan.shoppingcart.data.mapper.toCartItemEntity
import com.fikrathhassan.shoppingcart.data.mapper.toItem
import com.fikrathhassan.shoppingcart.domain.datasource.CartDataSource
import com.fikrathhassan.shoppingcart.domain.model.Item
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartDataSourceImpl @Inject constructor(
    private val itemDao: ItemDao,
    private val ioDispatcher: CoroutineDispatcher
) : CartDataSource {

    @WorkerThread
    override fun getItemList() = itemDao.getCartItemList().map { list ->
        list.map {
            it.toItem()
        }
    }.flowOn(ioDispatcher)

    @WorkerThread
    override fun getCartTotals() = itemDao.getCartTotals().flowOn(ioDispatcher)

    @WorkerThread
    override fun addItem(
        item: Item
    ) = flow {
        var quantity = 1
        itemDao.getItemById(item.id)?.let {
            it.quantity += quantity
            itemDao.updateItem(it)
            quantity = it.quantity
        } ?: itemDao.insertItem(item.copy(quantity = quantity).toCartItemEntity())
        emit(quantity)
    }.flowOn(ioDispatcher)

    @WorkerThread
    override fun increaseItemQuantity(
        itemId: String
    ) = flow {
        itemDao.getItemById(itemId)?.let {
            it.quantity += 1
            itemDao.updateItem(it)
        }
        emit(true)
    }.flowOn(ioDispatcher)

    @WorkerThread
    override fun reduceItemQuantity(
        itemId: String
    ) = flow {
        itemDao.getItemById(itemId)?.let {
            if (it.quantity <= 1) {
                itemDao.deleteItem(it)
            } else {
                it.quantity -= 1
                itemDao.updateItem(it)
            }
        }
        emit(true)
    }.flowOn(ioDispatcher)

}