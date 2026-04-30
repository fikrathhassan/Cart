package com.fikrathhassan.shoppingcart

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import java.text.NumberFormat
import java.util.Locale

fun Context.showShortToast(message: String?) {
    message?.let {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun Context.showLongToast(message: String?) {
    message?.let {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}

fun Long?.orZero() = this ?: 0
fun Int?.orZero() = this ?: 0
fun Double?.orZero() = this ?: 0.00

fun Context.getColorFromId(id: Int) = ContextCompat.getColor(this, id)

fun SwipeRefreshLayout.onRefresh(action: () -> Unit) {
    this.apply {
        setColorSchemeColors(context.getColorFromId(R.color.onBackground))
        setOnRefreshListener {
            isRefreshing = false
            action.invoke()
        }
    }
}

fun Double?.toRoundedTwoDecimal(): String {
    if (this == null || this <= 0.0) return "0.00"

    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return formatter.format(this)
}