package com.example.notes.util

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

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

fun View.visibleOnCondition(isVisible: Boolean) {
    visibility = if (isVisible) VISIBLE
    else GONE
}