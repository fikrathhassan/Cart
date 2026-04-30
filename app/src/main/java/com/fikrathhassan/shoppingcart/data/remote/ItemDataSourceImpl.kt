package com.fikrathhassan.shoppingcart.data.remote

import android.content.Context
import androidx.annotation.WorkerThread
import com.fikrathhassan.shoppingcart.core.network.ResponseHandler
import com.fikrathhassan.shoppingcart.data.mapper.toItem
import com.fikrathhassan.shoppingcart.data.remote.api.ApiService
import com.fikrathhassan.shoppingcart.domain.datasource.ItemDataSource
import com.skydoves.sandwich.suspendOperator
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class ItemDataSourceImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
) : ItemDataSource {

    @WorkerThread
    override fun getItemList(
        onStart: () -> Unit,
        onComplete: () -> Unit
    ) = flow {
        apiService.getItems().suspendOperator(
            ResponseHandler(
                context = context,
                success = { result ->
                    val listFromNetwork = result.data.map {
                        it.toItem()
                    }
                    emit(listFromNetwork)
                }
            )
        )
    }.onStart { onStart() }.onCompletion { onComplete() }.flowOn(ioDispatcher)

}