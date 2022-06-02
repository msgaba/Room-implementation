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
    /** select query to read all the entries in database **/
    @Query("SELECT * FROM note_table")
    fun getNoteList(): Flow<List<Note>> /** Flow gives instant updates to the classes and reflects changes as soon as they occur **/

    /** insert query to add entry at the end in database **/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note): Long

    /** delete query to delete entry in database using ID **/
    @Query("DELETE FROM note_table WHERE id = :id")
    suspend fun deleteNote(id: Int)

    /** update query to update entry in database using ID **/
    @Query("UPDATE note_table SET title=:title, body=:body WHERE id = :id")
    fun updateNote(title: String, body: String, id: Int)
}