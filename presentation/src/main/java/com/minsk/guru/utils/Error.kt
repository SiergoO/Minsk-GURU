package com.minsk.guru.utils

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showError(error: Throwable) =
    Snackbar.make(this.requireView(), error.cause?.message.toString(), Snackbar.LENGTH_SHORT).show()