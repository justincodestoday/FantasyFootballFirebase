package com.fantasy.fantasyfootball.dialog

import android.content.Context
import android.content.DialogInterface
import com.fantasy.fantasyfootball.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ConfirmDialogs {
    fun showConfirmDialog(
        context: Context,
        title: String,
        message: String,
        listener: DialogInterface.OnClickListener
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.dialog_button_positive, listener)
            .setNegativeButton(R.string.dialog_button_negative, null)
            .show()
    }
}