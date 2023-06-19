package com.muslim.simplenotesapp.utils

import androidx.compose.runtime.mutableStateOf
import com.muslim.simplenotesapp.domain.DatabaseRepository

const val TYPE_DATABASE = "type_database"
const val TYPE_ROOM = "type_room"
const val TYPE_FIREBASE = "type_firebase"
const val FIREBASE_ID = "firebaseId"

lateinit var REPOSITORY: DatabaseRepository
lateinit var LOGIN: String
lateinit var PASSWORD: String
var DB_TYPE = mutableStateOf("")

object Constants {

    object Keys {
        const val NOTES_DATABASE = "notes_database"
        const val NOTES_TABLE = "notes_table"
        const val NOTE_TITLE = "Заголовок"
        const val NOTE_SUBTITLE = "Начните писать"
        const val ADD_NOTE = "Добавить"
        const val TITLE = "title"
        const val SUBTITLE = "subtitle"
        const val WHAT_WILL_WE_USE = "Выберите место хранения"
        const val ROOM_DATABASE = "Локальное сохранение"
        const val FIREBASE_DATABASE = "Удаленное сохранение"
        const val ID = "id"
        const val UPDATE = "Изменить"
        const val DELETE = "Удалить"
        const val EDIT_NOTE = "Редактировать"
        const val EMPTY = ""
        const val UPDATE_NOTE = "Сохранить"
        const val SING_IN = "Sing in"
        const val LOG_IN = "Log In"
        const val LOGIN_TEXT = "Login"
        const val PASSWORD_TEXT = "Password"
    }

    object Screens {

        const val START_SCREEN = "start_screen"
        const val MAIN_SCREEN = "main_screen"
        const val ADD_SCREEN = "add_screen"
        const val NOTE_SCREEN = "note_screen"
        const val CALCULATOR_SCREEN = "calculator_screen"

    }
}