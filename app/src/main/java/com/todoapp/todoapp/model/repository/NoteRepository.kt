package com.todoapp.todoapp.model.repository

import androidx.lifecycle.LiveData
import com.todoapp.todoapp.model.entity.Note
import com.todoapp.todoapp.model.entity.NoteDao

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDao.insert(note)
    }

    suspend fun delete(note: Note) {
        noteDao.delete(note)
    }

}