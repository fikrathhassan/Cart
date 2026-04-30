package com.fikrathhassan.shoppingcart.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.fikrathhassan.shoppingcart.databinding.ItemShoppingItemBinding
import com.fikrathhassan.shoppingcart.ui.model.ItemUi

class CartItemAdapter : RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {

    interface ClickListener {
        fun onIncreaseQuantityClick(item: ItemUi, data: List<ItemUi>)
        fun onReduceQuantityClick(item: ItemUi, data: List<ItemUi>)
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

                btnAddToCart.isVisible = false
                clAddEdit.isVisible = true

                btnReduceQuantity.setOnClickListener {
                    if (item.quantity <= 1) {
                        item.quantity = 0
                        notifyItemRemoved(bindingAdapterPosition)
                    } else {
                        item.quantity -= 1
                        notifyItemChanged(bindingAdapterPosition)
                    }
                    _clickListener?.onReduceQuantityClick(item, _dataList)
                }
                btnIncreaseQuantity.setOnClickListener {
                    item.quantity += 1
                    notifyItemChanged(bindingAdapterPosition)
                    _clickListener?.onIncreaseQuantityClick(item, _dataList)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemShoppingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartItemAdapter.ViewHolder, position: Int) {
        holder.bind(_dataList[position])
    }

    override fun getItemCount(): Int {
        return _dataList.size
    }

    fun addDataToList(data: List<ItemUi>) {
        val oldCount = itemCount
        _dataList.addAll(data)
        notifyItemRangeInserted(oldCount, data.size)
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