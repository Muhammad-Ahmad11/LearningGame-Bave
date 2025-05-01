package com.example.learninggame.ui.screen

import android.speech.tts.TextToSpeech
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.learninggame.R
import com.example.learninggame.viewmodel.GameViewModel


@Composable
fun GameScreen(viewModel: GameViewModel = viewModel()) {

    val context = LocalContext.current

    val tts = remember {
        var ttsInstance: TextToSpeech? = null
        ttsInstance = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                ttsInstance?.language = java.util.Locale.US
            }
        }
        ttsInstance
    }

    val question by viewModel.question.collectAsState()
    val selectedOptionIndex by viewModel.selectedOptionIndex.collectAsState()
    val showHint by viewModel.showHint.collectAsState()
    val showQuestionText by viewModel.showQuestionText.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setTextToSpeech(tts)
    }

    Column {
        // Top App Bar
        TopAppBar(
            backgroundColor = Color.White,
            elevation = 0.dp,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Home Button
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF42A5F5))
                    ) {
                        IconButton(
                            onClick = { /* TODO: Navigate Home */ },
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_home),
                                contentDescription = "Home",
                                tint = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Question Section with speaker + question + hint

                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color(0xFF42A5F5))
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Speaker Icon
                        Icon(
                            painter = painterResource(id = R.drawable.ic_speaker),
                            contentDescription = "Speaker",
                            tint = Color.White,
                            modifier = Modifier.clickable {
                                tts.speak(question.questionText, TextToSpeech.QUEUE_FLUSH, null, null)
                            }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        // Divider
                        if (showQuestionText) {
                            Box(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(24.dp)
                                    .background(Color.White)
                            )
                        } else {
                            Spacer(modifier = Modifier.width(1.dp))
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Question Text
                        Text(
                            text = question.questionText,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.alpha(if (showQuestionText) 1f else 0f)  // Visible when true, hidden when false
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        // Hint Icon
                        Icon(
                            painter = painterResource(id = R.drawable.ic_lightbulb),
                            contentDescription = "Hint",
                            tint = Color.White,
                            modifier = Modifier.clickable { viewModel.toggleHint() }
                        )
                    }




                    Spacer(modifier = Modifier.width(12.dp))

                    // Settings Button
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF42A5F5))
                    ) {
                        IconButton(
                            onClick = { viewModel.toggleQuestionText() },
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_settings),
                                contentDescription = "Settings",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        )

        if (showHint) {
            AlertDialog(
                onDismissRequest = { viewModel.toggleHint() },
                title = { Text("Hint") },
                text = { Text(question.hint) },
                confirmButton = {
                    Button(onClick = { viewModel.toggleHint() }) {
                        Text("Close")
                    }
                }
            )
        }

        // Main Area
        Row(modifier = Modifier.fillMaxSize()) {
            // Left Side
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = {
                    tts.speak(question.leftText, TextToSpeech.QUEUE_FLUSH, null, null)
                }) {
                    Icon(painterResource(id = R.drawable.ic_speaker), contentDescription = "Left Speaker")
                }
                Image(
                    painter = painterResource(id = question.leftImageRes),
                    contentDescription = "Left Image",
                    modifier = Modifier.size(120.dp)
                )
                Text(
                    text = question.leftText,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF42A5F5)
                )
            }

            // Right Side
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = {
                    tts.speak("Select the correct image", TextToSpeech.QUEUE_FLUSH, null, null)
                }) {
                    Icon(painterResource(id = R.drawable.ic_speaker), contentDescription = "Instruction Speaker")
                }

                question.options.forEachIndexed { index, option ->
                    val correctIndex = question.correctAnswerIndex
                    val selected = selectedOptionIndex
                    val wrongAttempts by viewModel.wrongAttempts.collectAsState()
                    val firstWrongIndex by viewModel.firstWrongIndex.collectAsState()

                    val animatedColor = remember { Animatable(Color.White) }

                    val flashCorrect = selected == correctIndex && wrongAttempts >= 2

                    LaunchedEffect(flashCorrect) {
                        if (flashCorrect) {
                            repeat(3) {
                                animatedColor.animateTo(Color.Green, tween(200))
                                animatedColor.animateTo(Color.Blue, tween(200))
                            }
                        }
                    }

                    val backgroundColor = when {
                        selected == index && index == correctIndex && flashCorrect -> animatedColor.value
                        selected == index && index == correctIndex -> Color.Green
                        firstWrongIndex == index -> Color.Red
                        selected != null && selected != index && wrongAttempts >= 2 -> Color.Red
                        else -> Color.White
                    }

                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .size(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(backgroundColor)
                            .border(2.dp, Color(0xFF42A5F5), RoundedCornerShape(12.dp))
                            .clickable { viewModel.selectOption(index) },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = option.imageRes),
                                    contentDescription = option.text,
                                    modifier = Modifier.size(64.dp)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(30.dp)
                                    .background(Color(0xFF42A5F5)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = option.text,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}
