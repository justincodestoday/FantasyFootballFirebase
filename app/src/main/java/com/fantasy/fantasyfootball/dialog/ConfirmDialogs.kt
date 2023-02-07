package com.fantasy.fantasyfootball.dialog

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import com.fantasy.fantasyfootball.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ConfirmDialogs : AppCompatActivity() {
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
}