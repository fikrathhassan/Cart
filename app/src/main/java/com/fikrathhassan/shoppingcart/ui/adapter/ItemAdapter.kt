package com.fikrathhassan.shoppingcart.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.fikrathhassan.shoppingcart.databinding.ItemShoppingItemBinding
import com.fikrathhassan.shoppingcart.ui.model.ItemUi

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    interface ClickListener {
        fun onAddToCartClick(item: ItemUi)
    }

    private val _dataList = mutableListOf<ItemUi>()
    private var _clickListener: ClickListener? = null

    inner class ViewHolder(private val binding: ItemShoppingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemUi) {
            binding.apply {
                tvName.text = item.name
                tvRate.text = item.price.formattedPrice
                tvQuantity.text = item.quantity.toString()

                btnAddToCart.isVisible = true
                clAddEdit.isVisible = false

                btnAddToCart.setOnClickListener {
                    _clickListener?.onAddToCartClick(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        val view =
            ItemShoppingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        holder.bind(_dataList[position])
    }

    override fun getItemCount(): Int {
        return _dataList.size
    }

    fun replaceDataList(data: List<ItemUi>) {
        val oldCount = itemCount
        _dataList.clear()
        notifyItemRangeRemoved(0, oldCount)

        _dataList.addAll(data)
        notifyItemRangeInserted(0, data.size)
    }

    fun setClickListener(listener: ClickListener) {
        _clickListener = listener
    }
}