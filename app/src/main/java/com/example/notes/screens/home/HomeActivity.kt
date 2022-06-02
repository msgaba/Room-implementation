package com.example.notes.screens.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.database.NoteApplication
import com.example.notes.database.NoteViewModel
import com.example.notes.database.NoteViewModelFactory
import com.example.notes.databinding.ActivityHomeBinding
import com.example.notes.models.Dialog
import com.example.notes.models.Note
import com.example.notes.screens.dialog.CustomDialog
import com.example.notes.screens.dialog.DialogActionListener
import com.example.notes.screens.note.NoteActivity
import com.example.notes.util.*
import com.google.gson.Gson

class HomeActivity : AppCompatActivity(), DialogActionListener, NoteItemClickListener {
    private lateinit var binding: ActivityHomeBinding

    private lateinit var mAdapter: NoteListAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private var mNote = Note()
    lateinit var dialogActionListener: DialogActionListener
    private val noteViewModel: NoteViewModel by viewModels {
        NoteViewModelFactory((application as NoteApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialogActionListener = this
        ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            Pair.create(binding.icon, getString(R.string.image_logo_transition)),
        )
        initVars()
        viewClicks()
        observeEvents()
    }

    private fun observeEvents() {
        noteViewModel.noteList.observe(this, Observer {
            it?.let {
                binding.apply {
                    list.visibleOnCondition(it.isNotEmpty())
                    emptyView.container.visibleOnCondition(it.isEmpty())
                }
                mAdapter.list(it as MutableList<Note>)
            }
        })
        noteViewModel.addedItemId.observe(this, Observer {
            it?.let {
                mNote.id = it
                NoteActivity.navigate(this, mNote)
                mNote = Note()
            }
        })
    }

    private fun initVars() {
        mAdapter = NoteListAdapter(this)
        mLayoutManager = GridLayoutManager(this, 2)
        binding.list.apply {
            adapter = mAdapter
            layoutManager = mLayoutManager
        }
        mAdapter.listener(this)
    }

    private fun viewClicks() {
        binding.fab.setOnClickListener {
            CustomDialog.showDialog(
                this@HomeActivity,
                Dialog(
                    title = getString(R.string.name_dialog_title),
                    editViewText = getString(R.string.title),
                    button1Title = getString(R.string.save),
                    isButton2Visible = false,
                ),
                this
            )
        }
    }

    override fun onButton1Click(data: Any?) {
        val title = data as String
        mNote = Note(title = title)
        noteViewModel.addNote(mNote)
    }

    override fun onButton2Click(data: Any?) {}

    override fun onNoteClicked(note: Note) {
        NoteActivity.navigate(this, note)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NOTE_REQUEST_CODE) {
            when (resultCode) {
                REFRESH_REQUEST_CODE -> {
                    data?.let {
                        if (data.hasExtra(KEY_NOTE_OBJECT))
                            noteViewModel.updateNote(
                                Gson().fromJson(
                                    it.getStringExtra(KEY_NOTE_OBJECT),
                                    Note::class.java
                                )
                            )
                    }
                }
                DELETE_REQUEST_CODE -> {
                    data?.let { noteViewModel.deleteNote(it.getIntExtra(KEY_NOTE_ID, -1)) }
                }
            }
        }
    }
}