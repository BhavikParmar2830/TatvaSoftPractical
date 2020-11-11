package com.tatvasoftpractical.base

import android.app.Application
import android.content.ClipData
import android.content.Context
import android.widget.Toast

abstract class BaseViewModel(application: Application) : RuntimePermissionViewModel(application) {

    private val TAG = BaseViewModel::class.java.simpleName

    val prefs by lazy {

    }

     fun copyTextToClipboard(ctx : Context,string : String) {

        val clipboardManager = ctx.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clipData = ClipData.newPlainText("text", string)
        clipboardManager.setPrimaryClip(clipData)
        Toast.makeText(ctx, "Text copied to clipboard", Toast.LENGTH_LONG).show()
    }

}