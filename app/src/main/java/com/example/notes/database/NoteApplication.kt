package com.example.notes.database

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * Created by Ankita
 */
class NoteApplication: Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
     private val database by lazy { NoteRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { NoteRepository(database.noteDao()) }
}