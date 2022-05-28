package com.example.notes.database

import androidx.annotation.WorkerThread
import com.example.notes.models.Note
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ankita
 */
class NoteRepository(private val dao: NoteDao) {
    val notesList: Flow<List<Note>> = dao.getNoteList()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addNote(note: Note) {
        dao.addNote(note)
    }
}