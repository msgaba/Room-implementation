package com.example.notes.screens.home

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.databinding.ItemNotesBinding
import com.example.notes.models.Note

/**
 * Created by Ankita
 */
class NoteListAdapter(private val mContext: Activity): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mNoteList: MutableList<Note> = arrayListOf()
    private lateinit var mListener: NoteItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemNotesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NoteListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder: NoteListViewHolder = holder as NoteListViewHolder
        viewHolder.listener = mListener
        viewHolder.bind(mContext, mNoteList[position], position)
    }

    override fun getItemCount(): Int = mNoteList.size

    fun list(list: MutableList<Note>) {
        mNoteList = list
        notifyDataSetChanged()
    }

    fun addNote(note: Note) {
        mNoteList.add(note)
        notifyDataSetChanged()
    }

    fun deleteNote(pos: Int) {
        mNoteList.removeAt(pos)
        notifyDataSetChanged()
    }

    fun updateNote(note: Note, pos: Int) {
        mNoteList.add(pos, note)
        mNoteList.removeAt(pos + 1)
        notifyDataSetChanged()
    }

    fun size(): Int = mNoteList.size

    fun listener(listener: NoteItemClickListener) {
        mListener = listener
    }
}