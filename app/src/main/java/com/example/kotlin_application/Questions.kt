package com.example.kotlin_application

data class Questions(
    val id: Int,
    val question: String,
    val firstAnswer: String,
    val secondAnswer: String,
    val thirdAnswer: String,
    val correctAnswer: Int
)
