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
    suspend fun addNote(note: Note): Long {
        return dao.addNote(note)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateNote(note: Note) {
        dao.updateNote(note.title, note.body, note.id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteNote(id: Int) {
        dao.deleteNote(id)
    }
}