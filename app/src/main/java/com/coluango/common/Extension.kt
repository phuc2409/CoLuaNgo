package com.coluango.common

import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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

fun Any?.toJson(): String = Gson().toJson(this)

fun <T : Any?> fromJson(json: String): T {
    val type = object : TypeToken<T>() {}.type
    return Gson().fromJson(json, type)
}