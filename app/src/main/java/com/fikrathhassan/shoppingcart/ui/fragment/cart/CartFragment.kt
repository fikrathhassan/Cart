package com.fikrathhassan.shoppingcart.ui.fragment.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.fikrathhassan.shoppingcart.databinding.FragmentCartBinding
import com.fikrathhassan.shoppingcart.onRefresh
import com.fikrathhassan.shoppingcart.toRoundedTwoDecimal
import com.fikrathhassan.shoppingcart.ui.activity.MainActivity
import com.fikrathhassan.shoppingcart.ui.adapter.CartItemAdapter
import com.fikrathhassan.shoppingcart.ui.base.BaseFragment
import com.fikrathhassan.shoppingcart.ui.model.ItemUi
import com.fikrathhassan.shoppingcart.ui.model.toItemUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<MainActivity, FragmentCartBinding>(), View.OnClickListener,
    CartItemAdapter.ClickListener {

    private val _adapter by lazy {
        CartItemAdapter().also {
            it.setClickListener(this)
        }
    }

    override val viewModel: CartViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCartBinding.inflate(inflater, container, false)

    override fun initData() {
        binding.srl.onRefresh {}

        initClickListeners()
        initRecyclerView()
        initObservers()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.btnBack.id,
            binding.btnAddItems.id -> currentActivity.clearCurrentFragment()
        }
    }

    override fun onIncreaseQuantityClick(item: ItemUi, data: List<ItemUi>) {
        viewModel.increaseItemQuantity(itemId = item.id)
    }

    override fun onReduceQuantityClick(item: ItemUi, data: List<ItemUi>) {
        viewModel.reduceItemQuantity(itemId = item.id)
    }

    private fun initClickListeners() {
        binding.btnBack.setOnClickListener(this)
        binding.btnAddItems.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            adapter = this@CartFragment._adapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun initObservers() {
        viewModel.apply {
            progress.observe(viewLifecycleOwner) {
                binding.progressBar.isVisible = it
            }
            items.observe(viewLifecycleOwner) { data ->
                val dataList = data.map {
                    it.toItemUi()
                }
                _adapter.replaceDataList(dataList)
                binding.apply {
                    tvNoData.isVisible = dataList.isEmpty()
                    btnAddItems.isVisible = dataList.isEmpty()
                }
            }
            cartTotals.observe(viewLifecycleOwner) {
                binding.apply {
                    tvSubtotalValue.text = it.subtotal.toRoundedTwoDecimal()
                    tvTaxValue.text = it.totalTax.toRoundedTwoDecimal()
                    tvTotalValue.text = it.total.toRoundedTwoDecimal()
                }
            }
        }
    }

}