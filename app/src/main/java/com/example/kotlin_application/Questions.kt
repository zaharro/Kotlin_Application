package com.example.kotlin_application

import androidx.room.Entity
import androidx.room.PrimaryKey

/*data class Questions(
    val id: Int,
    val question: String,
    val firstAnswer: String,
    val secondAnswer: String,
    val thirdAnswer: String,
    val correctAnswer: Int
)*/

@Entity(tableName = "questions")
data class Questions(
    @PrimaryKey val id: Int,
    val question: String,
    val firstAnswer: String,
    val secondAnswer: String,
    val thirdAnswer: String,
    val correctAnswer: Int
)