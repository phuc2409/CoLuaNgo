package com.coluango.base

import androidx.fragment.app.Fragment

/**
 * User: Quang Phúc
 * Date: 20-Oct-22
 * Time: 08:46 PM
 */
abstract class BaseFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected abstract fun initView()

    protected abstract fun handleListener()

    protected abstract fun observeViewModel()
}