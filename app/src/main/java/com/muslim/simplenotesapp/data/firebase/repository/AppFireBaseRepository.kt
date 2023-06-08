package com.muslim.simplenotesapp.data.firebase.repository

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.muslim.simplenotesapp.data.database_repository.DatabaseRepository
import com.muslim.simplenotesapp.data.model.Note
import com.muslim.simplenotesapp.utils.LOGIN
import com.muslim.simplenotesapp.utils.PASSWORD

class AppFireBaseRepository : DatabaseRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override val readAll: LiveData<List<Note>>
        get() = TODO("Not yet implemented")

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun singOut() {
        firebaseAuth.signOut()
    }

    override fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(LOGIN, PASSWORD)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {
                firebaseAuth.createUserWithEmailAndPassword(LOGIN, PASSWORD)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { onFail(it.message.toString()) }
            }
    }
}