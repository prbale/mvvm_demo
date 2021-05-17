package com.prbale.kotlinmvvm.base.extensions

import android.content.Context
import android.os.Handler
import android.view.View

/**
 * Show the view. (visibility = View.VISIBLE)
 */
fun View.show() : View {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
    return this
}

/**
 * Hide the view (visibility = View.GONE)
 */
fun View.gone() : View {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
    return this
}

fun runDelayed(action: () -> Unit) {
    Handler().postDelayed(action, 3000)
}

fun Context.hide(vararg input: View?) {
    for (item in input) {
        item?.gone()
    }
}