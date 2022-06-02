package com.example.notes.database

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * Created by Ankita
 */
class NoteApplication : Application() {
    /** using coroutine scope as we need not cancel it; it'll be torn down with the process**/
    val applicationScope = CoroutineScope(SupervisorJob())

    /** Using by lazy so the database and the repository are only created when they're needed
    rather than when the application starts**/
    val database by lazy { NoteRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { NoteRepository(database.noteDao()) }
}