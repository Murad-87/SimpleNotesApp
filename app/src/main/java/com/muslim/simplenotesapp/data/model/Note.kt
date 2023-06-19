package com.muslim.simplenotesapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.muslim.simplenotesapp.utils.Constants.Keys.NOTES_TABLE
import java.util.Date

@Entity(tableName = NOTES_TABLE)
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val title: String = "",
    @ColumnInfo
    val subtitle: String = "",
    val createdDate: Date = Date(),
    val firebaseId: String = ""
)
