package com.fantasy.fantasyfootball.dialog

import android.content.Context
import com.fantasy.fantasyfootball.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ErrorDialogs {
    fun showErrorDialog(
        context: Context,
        title: String,
        message: String
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton(R.string.dialog_button_neutral, null)
            .show()
    }
}