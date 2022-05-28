package com.example.notes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notes.models.Note
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Created by Ankita
 */
@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteRoomDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    private class NoteDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            mInstance?.let {
                scope.launch {
                    it.noteDao().addNote(Note())
                }
            }
        }
    }

    companion object {
        @Volatile
        private var mInstance: NoteRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): NoteRoomDatabase {
            return mInstance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteRoomDatabase::class.java,
                    "note_database"
                ).addCallback(NoteDatabaseCallback(scope))
                    .build()
                mInstance = instance
                instance
            }
        }
    }
}