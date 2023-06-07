package com.muslim.simplenotesapp.data.database_repository

import androidx.lifecycle.LiveData
import com.muslim.simplenotesapp.data.model.Note

interface DatabaseRepository {

    val readAll: LiveData<List<Note>>

    suspend fun create(note: Note, onSuccess: () -> Unit)

    suspend fun update(note: Note, onSuccess: () -> Unit)

    suspend fun delete(note: Note, onSuccess: () -> Unit)
}