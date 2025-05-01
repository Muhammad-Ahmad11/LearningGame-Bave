package com.example.learninggame.data.model

data class Question(
    val questionText: String,
    val hint: String,
    val leftImageRes: Int,
    val leftText: String,
    val options: List<Option>,
    val correctAnswerIndex: Int
)

data class Option(
    val imageRes: Int,
    val text: String
)
