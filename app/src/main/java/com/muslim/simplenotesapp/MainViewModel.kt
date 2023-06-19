package com.muslim.simplenotesapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.muslim.simplenotesapp.data.database_room.room.dao.NoteRoomDao
import com.muslim.simplenotesapp.data.database_room.room.repository.RoomRepositoryImpl
import com.muslim.simplenotesapp.data.firebase.repository.AppFireBaseRepository
import com.muslim.simplenotesapp.data.model.Note
import com.muslim.simplenotesapp.utils.Constants
import com.muslim.simplenotesapp.utils.DB_TYPE
import com.muslim.simplenotesapp.utils.REPOSITORY
import com.muslim.simplenotesapp.utils.TYPE_FIREBASE
import com.muslim.simplenotesapp.utils.TYPE_ROOM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val noteRoomDao: NoteRoomDao,
    private val appFireBaseRepository: AppFireBaseRepository,
    private val roomRepositoryImpl: RoomRepositoryImpl
) : AndroidViewModel(application) {

    val context = application

    fun initDatabase(type: String, onSuccess: () -> Unit) {
        Log.d("checkData", "MainViewModel initDatabase with $type")
        when (type) {
            TYPE_ROOM -> {
                noteRoomDao
                REPOSITORY = roomRepositoryImpl
                onSuccess()
            }

            TYPE_FIREBASE -> {
                REPOSITORY = appFireBaseRepository
                REPOSITORY.connectToDatabase(
                    { onSuccess() },
                    { Log.d("checkData", "Error $it") }
                )
            }
        }
    }

    fun addNote(note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.create(note) {
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun updateNote(note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.update(note = note) {
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun readAllNotes() = REPOSITORY.readAll

    fun deleteNote(note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.delete(note = note) {
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }

    fun signOut(onSuccess: () -> Unit) {
        when (DB_TYPE.value) {
            TYPE_FIREBASE,
            TYPE_ROOM -> {
                REPOSITORY.singOut()
                DB_TYPE.value = Constants.Keys.EMPTY
                onSuccess()
            }

            else -> {
                Log.d("checkData", "signOut: ELSE: ${DB_TYPE.value}")
            }
        }
    }
}