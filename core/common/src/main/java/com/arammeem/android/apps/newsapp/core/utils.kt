package com.arammeem.android.apps.newsapp.core

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat

fun Context.openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
    ContextCompat.startActivity(this, intent, null)
}