package com.aleesha.core.extension

import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(message: String) {
    Snackbar.make(this, message, BaseTransientBottomBar.LENGTH_LONG).show()
}

fun View.showSnackbar(@StringRes messageResId: Int) {
    Snackbar.make(this, this.context.getString(messageResId), BaseTransientBottomBar.LENGTH_LONG).show()
}

/**
 * Shows a long displayed Snackbar using the root of the ViewBinding
 */
fun showLongSnack(binding: ViewBinding, message: String) {
    Snackbar.make(binding.root, message, BaseTransientBottomBar.LENGTH_LONG).show()
}

/**
 * Shows a short displayed Snackbar using the root of the ViewBinding
 */
fun showShortSnack(binding: ViewBinding, message: String) {
    Snackbar.make(binding.root, message, BaseTransientBottomBar.LENGTH_SHORT).show()
}


fun View.showErrorSnackbar(errorMessage: String) {
    Snackbar.make(this, errorMessage, BaseTransientBottomBar.LENGTH_LONG).show()
}

fun View.showIndefiniteSnackBar(
    oldSnackBar: Snackbar?,
    message: String,
    retryMessage: String = "",
    action: ((v: View) -> Unit)? = null,
    color: Int = -1
): Snackbar {
    oldSnackBar?.dismiss()

    val snackbar = Snackbar.make(this, message, BaseTransientBottomBar.LENGTH_INDEFINITE)
    if (retryMessage.isNotBlank() && action != null) {
        snackbar.setAction(retryMessage, action)
    }
    if (color != -1) {
        snackbar.setActionTextColor(ContextCompat.getColor(this.context, color))
    }

    return snackbar
}

fun ViewGroup.addViews(views: List<View>) {
    views.forEach { this.addView(it) }
}

fun View.visibleOrGone(show: Boolean) {
    if (show) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}