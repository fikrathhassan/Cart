package com.fikrathhassan.shoppingcart.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.fikrathhassan.shoppingcart.showShortToast
import com.fikrathhassan.shoppingcart.ui.util.helper.livedata.EventObserver

abstract class BaseFragment<A : AppCompatActivity, T : ViewBinding> : Fragment() {

    private var _currentActivity: A? = null
    protected val currentActivity: A
        get() = _currentActivity as A
    private var _binding: T? = null
    protected val binding: T
        get() = _binding as T
    protected abstract val viewModel: BaseVM

    protected abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): T

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _currentActivity = requireActivity() as A
        _binding = inflateBinding(inflater, container)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initData()
    }

    private fun initObservers() {
        viewModel.apply {
            toastMessage.observe(viewLifecycleOwner, EventObserver {
                if (it.isNotBlank()) {
                    requireContext().showShortToast(it)
                }
            })
        }
    }

    abstract fun initData()

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }


}