package com.fikrathhassan.shoppingcart.core.network

import android.content.Context
import android.widget.Toast
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.message
import com.skydoves.sandwich.operators.ApiResponseSuspendOperator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** A common response operator for handling [ApiResponse]s regardless of its type. */
class ResponseHandler<T>(
    private val context: Context,
    private val success: suspend (ApiResponse.Success<T>) -> Unit,
    private val error: (() -> Unit)? = null
) : ApiResponseSuspendOperator<T>() {

    // handles error cases when the API request gets an error response.
    override suspend fun onSuccess(apiResponse: ApiResponse.Success<T>) {
        apiResponse.run {
            success(this)
        }
    }

    // handles error cases depending on the status code.
    // e.g., internal server error.
    override suspend fun onError(apiResponse: ApiResponse.Failure.Error) {
        apiResponse.run {
            showToast(message())
            error?.invoke()
        }
    }

    // handles exceptional cases when the API request gets an exception response.
    // e.g., network connection error, timeout.
    override suspend fun onException(apiResponse: ApiResponse.Failure.Exception) {
        apiResponse.run {
            showToast("Please check your connection or contact administrator if problem persists!")
        }
    }

    private suspend fun showToast(message: String) = withContext(Dispatchers.Main) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}