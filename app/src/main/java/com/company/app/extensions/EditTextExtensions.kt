package com.company.app.extensions

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("onKeyboardDone")
fun EditText.onKeyboardDone(f: (() -> Unit)?) {
    setOnEditorActionListener { _, i, _ ->
        if (i == EditorInfo.IME_ACTION_DONE) {
            f?.invoke()
        }

        /*
        Always return false to let the framework handle the rest of the event (like keyboard hiding)
         */
        false
    }
}
