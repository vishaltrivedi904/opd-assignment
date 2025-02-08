package com.example.khushibaby.utils

import android.content.Context
import android.content.Intent

object ShareUtil {

    fun shareSummary(context: Context, content: String) {
        // Create an implicit Intent to share content
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, content)  // Set the text content to share
            type = "text/plain"  // Content type is plain text
        }

        // Open the system share dialog
        context.startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

}