package com.muslim.simplenotesapp.di

import android.app.Application
import androidx.room.Room
import com.muslim.simplenotesapp.data.database_room.room.AppRoomDatabase
import com.muslim.simplenotesapp.data.database_room.room.dao.NoteRoomDao
import com.muslim.simplenotesapp.data.database_room.room.repository.RoomRepositoryImpl
import com.muslim.simplenotesapp.domain.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppRoomDatabase(application: Application): AppRoomDatabase {
        return Room.databaseBuilder(
            application,
            AppRoomDatabase::class.java,
            AppRoomDatabase.NOTES_DATABASE,
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteRoomDao(database: AppRoomDatabase) : NoteRoomDao {
        return database.getRoomDao()
    }

    @Provides
    @Singleton
    fun provideRoomRepository(noteRoomDao: NoteRoomDao) : DatabaseRepository {
        return RoomRepositoryImpl(noteRoomDao)
    }
}