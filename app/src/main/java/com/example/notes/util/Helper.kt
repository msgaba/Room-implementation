package com.example.notes.util

import android.content.Context
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast

/**
 * Created by Ankita
 */
/** key constants to be used throughout project **/
const val KEY_DIALOG_OBJECT = "key_dialog_obj"
const val KEY_NOTE_OBJECT = "key_note_obj"
const val KEY_NOTE_ID = "key_note_id"
const val NOTE_REQUEST_CODE = 101
const val REFRESH_REQUEST_CODE = 102
const val NO_CHANGE_REQUEST_CODE = 103
const val DELETE_REQUEST_CODE = 104

/** extension function to hide views **/
fun View.hide() {
    visibility = GONE
}

/** extension function to show views **/
fun View.show() {
    visibility = VISIBLE
}

fun View.visibleOnCondition(isVisible: Boolean) {
    visibility = if(isVisible) VISIBLE
    else GONE
}

/** extension function to display toast message **/
fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}