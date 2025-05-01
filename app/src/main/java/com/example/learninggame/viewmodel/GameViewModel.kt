package com.example.learninggame.viewmodel

import android.speech.tts.TextToSpeech
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.learninggame.data.model.Question
import com.example.learninggame.data.model.Option
import com.example.learninggame.R
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    private val _question = MutableStateFlow(generateSampleQuestion())
    val question: StateFlow<Question> = _question

    private val _selectedOptionIndex = MutableStateFlow<Int?>(null)
    val selectedOptionIndex: StateFlow<Int?> = _selectedOptionIndex

    private val _wrongAttempts = MutableStateFlow(0)
    val wrongAttempts: StateFlow<Int> = _wrongAttempts

    private val _firstWrongIndex = MutableStateFlow<Int?>(null)
    val firstWrongIndex: StateFlow<Int?> = _firstWrongIndex

    private val _showHint = MutableStateFlow(false)
    val showHint: StateFlow<Boolean> = _showHint

    private val _showQuestionText = MutableStateFlow(true)
    val showQuestionText: StateFlow<Boolean> = _showQuestionText

    private lateinit var tts: TextToSpeech

    fun setTextToSpeech(ttsInstance: TextToSpeech) {
        tts = ttsInstance
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun selectOption(index: Int) {
        if (_selectedOptionIndex.value != null && _wrongAttempts.value >= 2) return

        if (index == _question.value.correctAnswerIndex) {
            _selectedOptionIndex.value = index
            kotlinx.coroutines.GlobalScope.launch {
                kotlinx.coroutines.delay(2000)
                resetForNextQuestion()
            }
        } else {
            if (_wrongAttempts.value == 0) {
                _firstWrongIndex.value = index
                _wrongAttempts.value += 1
            } else {
                _wrongAttempts.value += 1
                if (_wrongAttempts.value >= 2) {
                    _selectedOptionIndex.value = _question.value.correctAnswerIndex
                }
            }
        }
    }

    private fun resetForNextQuestion() {
        _selectedOptionIndex.value = null
        _wrongAttempts.value = 0
        _firstWrongIndex.value = null
        _question.value = getNextSampleQuestion()
    }

    private fun getNextSampleQuestion(): Question {
        return if (_question.value.questionText == "Which Word Does Not Rhyme") {
            Question(
                questionText = "Which of following not a fruit?",
                hint = "Think about what you can eat!",
                leftImageRes = R.drawable.apple,
                leftText = "Apple",
                options = listOf(
                    Option(R.drawable.banana, "Banana"),
                    Option(R.drawable.car, "Car"),
                    Option(R.drawable.orange, "Orange"),
                    Option(R.drawable.grapes, "Grapes")
                ),
                correctAnswerIndex = 1
            )
        } else generateSampleQuestion()
    }

    fun toggleHint() {
        _showHint.value = !_showHint.value
    }

    fun toggleQuestionText() {
        _showQuestionText.value = !_showQuestionText.value
    }

    companion object {
        private fun generateSampleQuestion(): Question {
            return Question(
                questionText = "Which Word Does Not Rhyme",
                hint = "Think about similar ending sounds!",
                leftImageRes = R.drawable.sun,
                leftText = "Sun",
                options = listOf(
                    Option(R.drawable.cat, "Cat"),
                    Option(R.drawable.ic_fun, "Fun"),
                    Option(R.drawable.run, "Run"),
                    Option(R.drawable.bun, "Bun")
                ),
                correctAnswerIndex = 0
            )
        }
    }
}
