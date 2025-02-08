package com.example.khushibaby.utils

import androidx.compose.runtime.mutableStateOf

object DialogManager {
    var showDialog = mutableStateOf(false)
        private set

    fun show() {
        showDialog.value = true
    }

    fun isShowing(): Boolean {
        return showDialog.value
    }

    fun hide() {
        showDialog.value = false
    }
}