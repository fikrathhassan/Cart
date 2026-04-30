package com.fikrathhassan.shoppingcart.ui.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<T : ViewBinding> : DialogFragment() {

    private var _binding: T? = null
    protected val binding: T
        get() = _binding as T

    protected abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateBinding(inflater, container)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false

        initData()
    }

    override fun onStart() {
        super.onStart()

        dialog?.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            it.window?.let { window ->
                window.setLayout(width, height)
                window.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            }
        }
    }

    abstract fun initData()

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }


}