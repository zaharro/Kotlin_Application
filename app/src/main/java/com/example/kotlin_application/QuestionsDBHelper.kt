package com.example.kotlin_application

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class QuestionsDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY,
                $COL_QUESTION TEXT,               
                $COL_FIRSTANSWER TEXT,
                $COL_SECONDANSWER TEXT,
                $COL_THIRDANSWER TEXT,
                $COL_CORRECTANSWER INTEGER
            )
        """.trimIndent()
        db.execSQL(createTableQuery)

        insertQuestion(
            db,
            1,
            "Создает и разрабатывает компьютерные программы, приложения и веб-сайты. Часто работает с кодом и решает технические задачи.",
            "Программист",
            "Врач",
            "Учитель",
            1
        )
        insertQuestion(
            db,
            2,
            "Занимается лечением больных, ставит диагнозы и назначает лечение. Работает в больницах, поликлиниках или частных клиниках.",
            "Архитектор",
            "Врач",
            "Юрист",
            2
        )
        insertQuestion(
            db,
            3,
            "Проектирует и строит здания, дома и сооружения. Учитывает функциональность, безопасность и эстетику.",
            "Архитектор",
            "Повар",
            "Стоматолог",
            1
        )
        insertQuestion(
            db,
            4,
            "Защищает интересы клиентов в суде, консультирует по юридическим вопросам и составляет юридические документы.",
            "Юрист",
            "Актер",
            "Фермер",
            1
        )
        insertQuestion(
            db,
            5,
            "Передаёт знания, обучает и воспитывает детей в школе или других образовательных учреждениях.",
            "Инженер",
            "Дизайнер",
            "Педагог",
            3
        )
    }

    private fun insertQuestion(
        db: SQLiteDatabase, id: Int, question: String,
        firstAnswer: String, secondAnswer: String,
        thirdAnswer: String, correctAnswer: Int
    ) {
        val values = ContentValues().apply {
            put(COL_ID, id)
            put(COL_QUESTION, question)
            put(COL_FIRSTANSWER, firstAnswer)
            put(COL_SECONDANSWER, secondAnswer)
            put(COL_THIRDANSWER, thirdAnswer)
            put(COL_CORRECTANSWER, correctAnswer)
        }
        db.insert(TABLE_NAME, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getQuestionById(id: Int): Questions? {
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_NAME,
            arrayOf(
                COL_ID,
                COL_QUESTION,
                COL_FIRSTANSWER,
                COL_SECONDANSWER,
                COL_THIRDANSWER,
                COL_CORRECTANSWER
            ),
            "$COL_ID = ?",
            arrayOf(id.toString()), null, null, null
        )

        var question: Questions? = null
        if (cursor.moveToFirst()) {
            question = Questions(
                cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_QUESTION)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_FIRSTANSWER)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_SECONDANSWER)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_THIRDANSWER)),
                cursor.getInt(cursor.getColumnIndexOrThrow(COL_CORRECTANSWER))
            )
        }
        cursor.close()
        return question
    }

    companion object {
        private const val DATABASE_NAME = "questions.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "questions"

        private const val COL_ID = "id"
        private const val COL_QUESTION = "question"
        private const val COL_FIRSTANSWER = "firstAnswer"
        private const val COL_SECONDANSWER = "secondAnswer"
        private const val COL_THIRDANSWER = "thirdAnswer"
        private const val COL_CORRECTANSWER = "correctAnswer"

    }

}