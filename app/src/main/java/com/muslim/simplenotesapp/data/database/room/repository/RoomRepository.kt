package com.muslim.simplenotesapp.data.database.room.repository

import androidx.lifecycle.LiveData
import com.muslim.simplenotesapp.data.database.room.dao.NoteRoomDao
import com.muslim.simplenotesapp.data.database_repository.DatabaseRepository
import com.muslim.simplenotesapp.data.model.Note

class RoomRepository(private val noteRoomDao: NoteRoomDao): DatabaseRepository {
    override val readAll: LiveData<List<Note>>
        get() = noteRoomDao.getAllNotes()

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.addNote(note)
        onSuccess()
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.updateNote(note)
        onSuccess()
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.deleteNote(note)
        onSuccess()
    }

    override fun singOut() {}
}