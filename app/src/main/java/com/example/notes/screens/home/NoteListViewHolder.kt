package com.example.notes.screens.home

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.databinding.ItemNotesBinding
import com.example.notes.models.Note

/**
 * Created by Ankita
 */
class NoteListViewHolder(private val viewBinding: ItemNotesBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    lateinit var listener: NoteItemClickListener

    fun bind(context: Context, note: Note, pos: Int) {
        viewBinding.apply {
            title.text = note.title
            body.text = note.body
            val params: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            if (pos % 2 == 0) {
                params.setMargins(
                    0,
                    0,
                    context.resources.getDimensionPixelSize(R.dimen.dp_size_8),
                    context.resources.getDimensionPixelSize(R.dimen.dp_size_8)
                )
            } else params.setMargins(
                context.resources.getDimensionPixelSize(R.dimen.dp_size_8),
                0,
                0,
                context.resources.getDimensionPixelSize(R.dimen.dp_size_8)
            )
            containerNote.layoutParams = params
            containerNote.setOnClickListener {
                listener.onNoteClicked(note)
            }
        }
    }
}

interface NoteItemClickListener {
    fun onNoteClicked(note: Note)
}