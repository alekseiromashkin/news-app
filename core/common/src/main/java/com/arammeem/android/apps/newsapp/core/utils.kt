package com.arammeem.android.apps.newsapp.core

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import java.time.Instant
import java.time.OffsetDateTime
import java.util.*

fun Context.openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    }
    ContextCompat.startActivity(this, intent, null)
}

fun String.toOffsetDateTime(): OffsetDateTime {
    return if (this.isNotBlank())  {
        OffsetDateTime.parse(this)
    } else {
        OffsetDateTime.MIN
    }
}

fun Instant.capped(): Instant {
    val instants: Array<Instant> = arrayOf(
        Instant.ofEpochMilli(Long.MIN_VALUE),
        this,
        Instant.ofEpochMilli(Long.MAX_VALUE)
    )
    Arrays.sort(instants)
    return instants[1]
}
