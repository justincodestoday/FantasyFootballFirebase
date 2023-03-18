package com.fantasy.fantasyfootball.util

import android.content.Context
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.constant.Enums

object AlertUtil {
    fun enumToString(type: String?, context: Context): String {
        return when (type) {
            Enums.FormError.EMPTY_FIELD.name -> context.getString(R.string.empty_field)
            Enums.FormError.MISSING_NAME.name -> context.getString(R.string.missing_name)
            Enums.FormError.MISSING_TEAM_NAME.name -> context.getString(R.string.missing_team_name)
            Enums.FormError.MISSING_EMAIL.name -> context.getString(R.string.missing_email)
            Enums.FormError.MISSING_PASSWORD.name -> context.getString(R.string.missing_password)
            Enums.FormError.INVALID_EMAIL.name -> context.getString(R.string.invalid_email)
            Enums.FormError.INVALID_USERNAME.name -> context.getString(R.string.invalid_username)
            Enums.FormError.INVALID_PASSWORD.name -> context.getString(R.string.invalid_password)
            Enums.FormError.PASSWORDS_NOT_MATCHING.name -> context.getString(R.string.passwords_not_matching)
            Enums.FormError.USER_EXISTS.name -> context.getString(R.string.user_already_exists)
            Enums.FormError.TEAM_NAME_EXISTS.name -> context.getString(R.string.team_name_already_exists)
            Enums.FormError.WRONG_CREDENTIALS.name -> context.getString(R.string.wrong_credentials)
            Enums.FormError.IMAGE_UPLOAD_FAILED.name -> context.getString(R.string.image_upload_failed)
            Enums.FormError.FAILURE.name -> context.getString(R.string.failure)
            Enums.FormSuccess.REGISTER_SUCCESSFUL.name -> context.getString(R.string.register_successful)
            Enums.FormSuccess.LOGIN_SUCCESSFUL.name -> context.getString(R.string.login_successful)
            Enums.FormSuccess.LOGOUT_SUCCESSFUL.name -> context.getString(R.string.logout_successful)
            Enums.FormSuccess.UPDATE_SUCCESSFUL.name -> context.getString(R.string.update_successful)
            else -> context.getString(R.string.unrecognised_error)
        }
    }
}