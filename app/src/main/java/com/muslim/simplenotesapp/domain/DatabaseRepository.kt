package com.muslim.simplenotesapp.domain

import androidx.lifecycle.LiveData
import com.muslim.simplenotesapp.data.model.Note

interface DatabaseRepository {

    val readAll: LiveData<List<Note>>

    suspend fun create(note: Note, onSuccess: () -> Unit)

    suspend fun update(note: Note, onSuccess: () -> Unit)

    suspend fun delete(note: Note, onSuccess: () -> Unit)

    fun singOut() {}

    fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit) {}
}