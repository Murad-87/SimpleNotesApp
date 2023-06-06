package com.muslim.simplenotesapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muslim.simplenotesapp.data.model.Note
import com.muslim.simplenotesapp.utils.TYPE_FIREBASE
import com.muslim.simplenotesapp.utils.TYPE_ROOM

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val readTest: MutableLiveData<List<Note>> by lazy {
        MutableLiveData<List<Note>>()
    }
    val dbType: MutableLiveData<String> by lazy {
        MutableLiveData<String>(TYPE_ROOM)
    }

    init {
        readTest.value =
            when(dbType.value) {
                TYPE_ROOM -> {
                    listOf<Note>(
                        Note(title = "Note 1", subtitle = "Subtitle for nte 1"),
                        Note(title = "Note 2", subtitle = "Subtitle for nte 2"),
                        Note(title = "Note 3", subtitle = "Subtitle for nte 3"),
                        Note(title = "Note 4", subtitle = "Subtitle for nte 4"),
                    )
                }
                TYPE_FIREBASE -> listOf()
                else -> listOf()
            }
    }

    fun initDatabase(type: String) {
        dbType.value = type
        Log.d("checkData", "MainViewModel initDatabase with $type")
    }
}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}