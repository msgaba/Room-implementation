package com.example.notes.screens.home

import android.content.Intent
import android.os.Bundle
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
import com.example.notes.util.DELETE_REQUEST_CODE
import com.example.notes.util.KEY_NOTE_OBJECT
import com.example.notes.util.NOTE_REQUEST_CODE
import com.example.notes.util.REFRESH_REQUEST_CODE
import com.google.gson.Gson

class HomeActivity : AppCompatActivity(), DialogActionListener, NoteItemClickListener {
    private lateinit var binding: ActivityHomeBinding

    private lateinit var mAdapter: NoteListAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private var positionClicked = -1
    lateinit var dialogActionListener: DialogActionListener
    private val noteViewModel by lazy {
        NoteViewModelFactory((application as NoteApplication).repository).create(NoteViewModel::class.java)
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
        noteViewModel.noteList.observe(this, Observer {
            it?.let { mAdapter.list(it as MutableList<Note>) }
        })
    }

    private fun initVars() {
        mAdapter = NoteListAdapter(this)
        mLayoutManager = GridLayoutManager(this, 2)
        binding.list.apply {
            adapter = mAdapter
            layoutManager = mLayoutManager
        }
//        mAdapter.list(getNote())
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
        val note = Note(title = title)
        mAdapter.addNote(note)
        positionClicked = mAdapter.size() - 1
        NoteActivity.navigate(this, note)
    }

    override fun onButton2Click(data: Any?) {}

    override fun onNoteClicked(note: Note, pos: Int) {
        positionClicked = pos
        NoteActivity.navigate(this, note)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NOTE_REQUEST_CODE) {
            when (resultCode) {
                REFRESH_REQUEST_CODE -> {
                    data?.let {
                        if (data.hasExtra(KEY_NOTE_OBJECT))
                            noteViewModel.addNote(
                                Gson().fromJson(
                                    it.getStringExtra(KEY_NOTE_OBJECT),
                                    Note::class.java
                                )
                            )
                    }
                }
                DELETE_REQUEST_CODE -> {
                    mAdapter.deleteNote(positionClicked)
                }
            }
        }
    }
}