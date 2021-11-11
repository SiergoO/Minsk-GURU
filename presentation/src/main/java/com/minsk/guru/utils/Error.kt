package com.minsk.guru.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showError(error: Throwable) =
    Snackbar.make(this, error.cause?.message.toString(), Snackbar.LENGTH_SHORT).show()