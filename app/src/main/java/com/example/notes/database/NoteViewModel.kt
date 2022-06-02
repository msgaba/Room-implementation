package com.example.notes.database

import androidx.lifecycle.*
import com.example.notes.models.Note
import kotlinx.coroutines.launch

/**
 * Created by Ankita
 */
class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val noteList: LiveData<List<Note>> = repository.notesList.asLiveData()

    private val _addedItemId: MutableLiveData<Int> = MutableLiveData<Int>()
    val addedItemId: LiveData<Int> = _addedItemId

    fun addNote(note: Note) = viewModelScope.launch {
        _addedItemId.value = repository.addNote(note).toInt()
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

    fun deleteNote(id: Int) = viewModelScope.launch {
        repository.deleteNote(id)
    }
}

class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}