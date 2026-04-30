package com.fikrathhassan.shoppingcart.ui.fragment.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.fikrathhassan.shoppingcart.R
import com.fikrathhassan.shoppingcart.databinding.FragmentItemsBinding
import com.fikrathhassan.shoppingcart.onRefresh
import com.fikrathhassan.shoppingcart.orZero
import com.fikrathhassan.shoppingcart.toRoundedTwoDecimal
import com.fikrathhassan.shoppingcart.ui.activity.MainActivity
import com.fikrathhassan.shoppingcart.ui.adapter.ItemAdapter
import com.fikrathhassan.shoppingcart.ui.base.BaseFragment
import com.fikrathhassan.shoppingcart.ui.fragment.cart.CartFragment
import com.fikrathhassan.shoppingcart.ui.model.ItemUi
import com.fikrathhassan.shoppingcart.ui.model.toItem
import com.fikrathhassan.shoppingcart.ui.model.toItemUi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ItemsFragment : BaseFragment<MainActivity, FragmentItemsBinding>(), View.OnClickListener,
    ItemAdapter.ClickListener {

    private val _adapter by lazy {
        ItemAdapter().also {
            it.setClickListener(this)
        }
    }

    override val viewModel: ItemsViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentItemsBinding.inflate(inflater, container, false)

    override fun initData() {
        binding.srl.onRefresh {
            viewModel.getItems()
        }

        initClickListeners()
        initRecyclerView()
        initObservers()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnCart -> currentActivity.replaceFragment(
                fragment = CartFragment()
            )
        }
    }

    override fun onAddToCartClick(item: ItemUi) {
        viewModel.addItem(item = item.toItem())
    }

    private fun initClickListeners() {
        binding.btnCart.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            adapter = this@ItemsFragment._adapter
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
            itemList.observe(viewLifecycleOwner) { data ->
                val dataList = data.map {
                    it.toItemUi()
                }
                _adapter.replaceDataList(dataList)
                binding.tvNoData.isVisible = dataList.isEmpty()
            }
            cartTotals.observe(viewLifecycleOwner) {
                binding.apply {
                    cvTotal.isVisible = it.total.orZero() > 0
                    tvTotalIncTaxValue.text = it.total.toRoundedTwoDecimal()
                }
            }
        }
    }

}