package com.example.notes.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.notes.models.Note
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

/**
 * Created by Ankita
 */
class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val noteList: LiveData<List<Note>> = repository.notesList as LiveData<List<Note>>

    fun addNote(note: Note) = viewModelScope.launch {
        repository.addNote(note)
    }
}

class NoteViewModelFactory(private val repository: NoteRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}