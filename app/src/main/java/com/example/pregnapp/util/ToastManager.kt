package com.example.pregnapp.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.test.espresso.IdlingResource

import androidx.test.espresso.idling.CountingIdlingResource


object ToastManager {
    private val idlingResource: CountingIdlingResource = CountingIdlingResource("toast")
    private val listener: View.OnAttachStateChangeListener = object :
        View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(p0: View) {
            idlingResource.increment()
        }

        override fun onViewDetachedFromWindow(p0: View) {
            idlingResource.decrement()
        }
    }

    fun makeText(context: Context?, text: CharSequence?, duration: Int): Toast {
        val t = Toast.makeText(context, text, duration)
        t.view!!.addOnAttachStateChangeListener(listener)
        return t
    }

    // For testing
    fun getIdlingResource(): IdlingResource {
        return idlingResource
    }
}