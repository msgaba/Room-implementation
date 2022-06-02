package com.example.notes.screens.note

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.widget.doOnTextChanged
import com.example.notes.R
import com.example.notes.databinding.ActivityNoteBinding
import com.example.notes.models.Dialog
import com.example.notes.models.Note
import com.example.notes.screens.dialog.CustomDialog
import com.example.notes.screens.dialog.DialogActionListener
import com.example.notes.util.*
import com.google.gson.Gson

/**
 * Created by Ankita
 */
const val BACK_CLICKED = 1
const val TITLE_CLICKED = 2
const val CLEAR_CLICKED = 3
const val SAVE_CLICKED = 4
const val DELETE_CLICKED = 5

class NoteActivity : AppCompatActivity(), DialogActionListener {

    private lateinit var binding: ActivityNoteBinding
    private var note = Note()
    private var updatedNote = Note()
    private var viewClicked = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent?.extras?.let {
            note = Gson().fromJson(intent.getStringExtra(KEY_NOTE_OBJECT), Note::class.java)
            updatedNote = note
        }
        initViews()
        editAction()
        viewClicks()
    }

    private fun initViews() {
        binding.apply {
            title.text = note.title
            body.setText(note.body)
        }
    }

    private fun editAction() {
        binding.body.doOnTextChanged { text, _, _, _ ->
            updatedNote.body = text.toString()
        }
    }

    private fun viewClicks() {
        binding.back.containerBack.setOnClickListener {
            viewClicked = BACK_CLICKED
            CustomDialog.showDialog(
                this@NoteActivity,
                Dialog(
                    title = getString(R.string.back_dialog_title),
                    button1Title = getString(R.string.yes),
                    button2Title = getString(R.string.no),
                    isEditViewVisible = false,
                ),
                this@NoteActivity
            )
        }
        binding.title.setOnClickListener {
            viewClicked = TITLE_CLICKED
            CustomDialog.showDialog(
                this@NoteActivity,
                Dialog(
                    title = getString(R.string.name_dialog_title),
                    editViewText = binding.title.text.toString(),
                    button1Title = getString(R.string.save),
                    isButton2Visible = false,
                ),
                this@NoteActivity
            )
        }
        binding.bottomFunc.apply {
            clear.containerClear.setOnClickListener {
                viewClicked = CLEAR_CLICKED
                CustomDialog.showDialog(
                    this@NoteActivity,
                    Dialog(
                        title = getString(R.string.clear_dialog_title),
                        button1Title = getString(R.string.yes),
                        button2Title = getString(R.string.no),
                        isEditViewVisible = false,
                    ),
                    this@NoteActivity
                )
            }
            save.containerSave.setOnClickListener {
                viewClicked = SAVE_CLICKED
                CustomDialog.showDialog(
                    this@NoteActivity,
                    Dialog(
                        title = getString(R.string.save_dialog_title),
                        button1Title = getString(R.string.yes),
                        button2Title = getString(R.string.no),
                        isEditViewVisible = false,
                    ),
                    this@NoteActivity
                )
            }
            delete.containerDelete.setOnClickListener {
                viewClicked = DELETE_CLICKED
                CustomDialog.showDialog(
                    this@NoteActivity,
                    Dialog(
                        title = getString(R.string.delete_dialog_title),
                        button1Title = getString(R.string.yes),
                        button2Title = getString(R.string.no),
                        isEditViewVisible = false,
                    ),
                    this@NoteActivity
                )
            }
        }
    }

    override fun onButton1Click(data: Any?) {
        when (viewClicked) {
            BACK_CLICKED -> {
                setResult(NO_CHANGE_REQUEST_CODE)
                finish()
            }
            TITLE_CLICKED -> {
                binding.title.text = data as String
                updatedNote.title = data
            }
            CLEAR_CLICKED -> {
                val intent = Intent()
                intent.putExtra(KEY_NOTE_OBJECT, Gson().toJson(Note(id = note.id)))
                setResult(REFRESH_REQUEST_CODE, intent)
                finish()
            }
            SAVE_CLICKED -> {
                val intent = Intent()
                intent.putExtra(KEY_NOTE_OBJECT, Gson().toJson(updatedNote))
                setResult(REFRESH_REQUEST_CODE, intent)
                finish()
            }
            DELETE_CLICKED -> {
                val intent = Intent()
                intent.putExtra(KEY_NOTE_ID, note.id)
                setResult(DELETE_REQUEST_CODE, intent)
                finish()
            }
        }
    }

    override fun onBackPressed() {
        viewClicked = BACK_CLICKED
        CustomDialog.showDialog(
            this@NoteActivity,
            Dialog(
                title = getString(R.string.back_dialog_title),
                button1Title = getString(R.string.yes),
                button2Title = getString(R.string.no),
                isEditViewVisible = false,
            ),
            this@NoteActivity
        )
    }

    override fun onButton2Click(data: Any?) {}

    companion object {

        fun navigate(mContext: Activity, note: Note) {
            val intent = Intent(mContext, NoteActivity::class.java)
            intent.putExtra(KEY_NOTE_OBJECT, Gson().toJson(note))
            ActivityCompat.startActivityForResult(mContext, intent, NOTE_REQUEST_CODE, null)
        }
    }
}