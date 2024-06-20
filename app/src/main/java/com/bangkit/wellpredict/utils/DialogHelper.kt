package com.bangkit.wellpredict.utils

import android.app.Dialog
import android.content.Context
import com.bangkit.wellpredict.R
import com.saadahmedev.popupdialog.PopupDialog

class DialogHelper {
    companion object {
        fun errorDialog(context: Context, descriptionText: String) {
            PopupDialog.getInstance(context)
                .statusDialogBuilder()
                .createErrorDialog()
                .setHeading("Uh-Oh")
                .setActionButtonText("Dismiss")
                .setDescription(
                    descriptionText +
                            ". Try again."
                )
                .build(Dialog::dismiss)
                .show()
        }

        fun successDialog(context: Context, headingText: String, descriptionText: String, actionText: String) {
            PopupDialog.getInstance(context)
                .statusDialogBuilder()
                .createSuccessDialog()
                .setHeading(headingText)
                .setDescription(descriptionText)
                .setActionButtonText(actionText)
                .build(Dialog::dismiss)
                .show()
        }

        fun loadingDialog(context: Context): PopupDialog {
            val dialog = PopupDialog.getInstance(context)
                .progressDialogBuilder()
                .createLottieDialog()
                .setRawRes(R.raw.heart_animation)
                .build()

            dialog.show()
            return dialog
        }
    }
}