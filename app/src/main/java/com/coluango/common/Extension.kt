package com.coluango.common

import android.view.View

/**
 * User: Quang Phúc
 * Date: 20-Oct-22
 * Time: 11:26 PM
 */
fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}