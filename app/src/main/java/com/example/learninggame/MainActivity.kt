package com.example.learninggame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.learninggame.ui.screen.GameScreen
import com.example.learninggame.ui.theme.LearningGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearningGameTheme {
                GameScreen()
            }
        }
    }
}
