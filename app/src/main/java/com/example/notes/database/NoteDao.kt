package com.example.notes.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
    suspend fun addNote(note: Note): Long

    @Query("DELETE FROM note_table WHERE id = :id")
    suspend fun deleteNote(id: Int)

    @Query("UPDATE note_table SET title=:title, body=:body WHERE id = :id")
    fun updateNote(title: String, body: String, id: Int)
}