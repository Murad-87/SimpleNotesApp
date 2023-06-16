package com.muslim.simplenotesapp.domain

import com.google.firebase.auth.AuthResult
import com.muslim.simplenotesapp.utils.Resource
import kotlinx.coroutines.flow.Flow


interface AuthRepositoryFirebase {

    fun loginUser(email: String, password: String) : Flow<Resource<AuthResult>>

    fun registerUser(email: String, password: String) : Flow<Resource<AuthResult>>
}