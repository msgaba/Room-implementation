package com.example.notes.models

/**
 * Created by Ankita
 */
data class Dialog(
    var title: String = "",
    var editViewText: String = "",
    var button1Title: String = "",
    var button2Title: String = "",
    var isButton1Visible: Boolean = true,
    var isButton2Visible: Boolean = true,
    var isEditViewVisible: Boolean = true,
)