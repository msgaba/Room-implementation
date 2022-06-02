package com.example.notes.database

import androidx.lifecycle.*
import com.example.notes.models.Note
import kotlinx.coroutines.launch

/**
 * Created by Ankita
 */
class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val noteList: LiveData<List<Note>> = repository.notesList.asLiveData()

    // variable to store added item id which cant be accessed outside viewmodel class
    private val _addedItemId: MutableLiveData<Int> = MutableLiveData<Int>()
    // variable to store added item id which will be observed in view class
    val addedItemId: LiveData<Int> = _addedItemId

    /** function to add note in database**/
    fun addNote(note: Note) = viewModelScope.launch {
        _addedItemId.value = repository.addNote(note).toInt()
    }

    /** function to update note in database**/
    fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
    }

    /** function to delete note in database**/
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