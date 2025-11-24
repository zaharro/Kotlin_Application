package com.example.kotlin_application

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Dao
import androidx.room.OnConflictStrategy

@Dao
interface QuestionsDao {
    @Query("SELECT * FROM questions WHERE id = :id")
    suspend fun getQuestionById(id: Int): Questions?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<Questions>)

}