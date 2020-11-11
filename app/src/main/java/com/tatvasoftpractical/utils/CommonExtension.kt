package com.example.appideas.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.appcompat.widget.AppCompatEditText
import java.math.BigDecimal

fun Context.isNightMode() =
    resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

fun Context.layoutInflater() = (this as Activity).layoutInflater

fun AppCompatEditText.hidePassword(isHide: Boolean) {
    transformationMethod =
        if (isHide) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()
    setSelection(length())
}