package com.todoapp.todoapp.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.todoapp.todoapp.databinding.ActivityMainBinding
import com.todoapp.todoapp.model.entity.Note
import com.todoapp.todoapp.view.adapter.INotesRVAdapter
import com.todoapp.todoapp.view.adapter.NotesRVAdapter
import com.todoapp.todoapp.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity(), INotesRVAdapter, View.OnClickListener {

    lateinit var viewModel: NoteViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this, this)
        binding.recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer {list ->
            list?.let {
                adapter.updateList(it)
            }
        })
    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
        Snackbar.make(binding.root, "${note.text} Deleted", Snackbar.LENGTH_SHORT).show()
    }

    override fun onClick(view: View?) {
        val noteText = binding.input.text.toString()
        if(noteText.isNotEmpty()) {
            viewModel.insertNote(Note(noteText))
            Snackbar.make(binding.root, "${noteText} Added", Snackbar.LENGTH_SHORT).show()
        }
    }
}