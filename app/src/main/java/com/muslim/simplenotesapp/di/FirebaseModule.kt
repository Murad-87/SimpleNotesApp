package com.muslim.simplenotesapp.di

import com.google.firebase.auth.FirebaseAuth
import com.muslim.simplenotesapp.data.firebase.repository.AppFireBaseRepository
import com.muslim.simplenotesapp.domain.AuthRepositoryFirebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAppFireBaseRepository(firebaseAuth: FirebaseAuth) : AuthRepositoryFirebase {
        return AppFireBaseRepository(firebaseAuth)
    }
}