package com.fikrathhassan.shoppingcart.ui.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.fikrathhassan.shoppingcart.R
import com.fikrathhassan.shoppingcart.databinding.ActivityMainBinding
import com.fikrathhassan.shoppingcart.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun initData() {}

    fun replaceFragment(
        fragment: Fragment,
        isTransitionEnabled: Boolean = true,
        addToBackStack: Boolean = true
    ) {
        supportFragmentManager.commit {
            if (isTransitionEnabled) {
                setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            }
            replace(R.id.container, fragment, fragment::class.java.simpleName)
            if (addToBackStack) {
                addToBackStack(fragment::class.java.simpleName)
            }
        }
    }

    fun clearCurrentFragment() {
        supportFragmentManager.popBackStack()
    }
}