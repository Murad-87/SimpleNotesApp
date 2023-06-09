package com.muslim.simplenotesapp.data.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.muslim.simplenotesapp.data.database.room.dao.NoteRoomDao
import com.muslim.simplenotesapp.data.model.Note
import com.muslim.simplenotesapp.utils.Constants.Keys.NOTES_DATABASE

@Database(entities = [Note::class], version = 2)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun getRoomDao(): NoteRoomDao

    companion object {

        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getInstance(context: Context) : AppRoomDatabase {
            return if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppRoomDatabase::class.java,
                    NOTES_DATABASE
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE as AppRoomDatabase
            } else INSTANCE as AppRoomDatabase
        }
    }
}