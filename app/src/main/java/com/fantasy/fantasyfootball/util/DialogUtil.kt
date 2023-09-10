package com.fantasy.fantasyfootball.util

import android.content.Context
import android.content.DialogInterface
import com.fantasy.fantasyfootball.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DialogUtil {
    fun showConfirmDialog(
        context: Context,
        title: String,
        message: String,
        listener: DialogInterface.OnClickListener
    ) {
        MaterialAlertDialogBuilder(context, R.style.Confirm_AlertDialog)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.dialog_button_positive, listener)
            .setNegativeButton(R.string.dialog_button_negative, null)
            .show()
    }

    fun showDeleteDialog(
        context: Context,
        title: String,
        message: String,
        listener: DialogInterface.OnClickListener
    ) {
        MaterialAlertDialogBuilder(context, R.style.Delete_AlertDialog)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.dialog_button_remove, listener)
            .setNegativeButton(R.string.dialog_button_negative, null)
            .show()
    }

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