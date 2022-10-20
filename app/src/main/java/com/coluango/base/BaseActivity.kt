package com.coluango.base

import androidx.appcompat.app.AppCompatActivity

/**
 * User: Quang Phúc
 * Date: 20-Oct-22
 * Time: 09:16 PM
 */
abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun initView()

    protected abstract fun handleListener()

    protected abstract fun observeViewModel()
}