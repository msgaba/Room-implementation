package com.example.notes.database

import androidx.room.*
import com.example.notes.models.Note
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ankita
 */
@Dao
interface NoteDao {
    @Query("SELECT * FROM note_table")
    fun getNoteList(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Query("DELETE FROM note_table WHERE id = :id")
    suspend fun deleteNote(id: Int)

    @Update()
    fun updateNote(id: Int)
}