package com.muslim.simplenotesapp.data.database_room.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.muslim.simplenotesapp.data.database_room.room.dao.NoteRoomDao
import com.muslim.simplenotesapp.data.model.Note

@Database(entities = [Note::class], version = 5)
@TypeConverters(DateConverter::class)
abstract class AppRoomDatabase : RoomDatabase() {

    abstract fun getRoomDao(): NoteRoomDao

    companion object {

        const val NOTES_DATABASE = "notes_database"

    }
}