# Learning Game – Kotlin (Jetpack Compose)

An educational Android game screen built using **Kotlin** and **Jetpack Compose** for Bave Company. The app presents children with interactive learning tasks, combining audio, images, and touch-based choices.

## 🎯 Features
- **Top Bar**: Home, Speaker (reads question), Hint (popup), Settings (toggle question text)
- **Left Panel**: Speaker, image (e.g., "Sun"), and label
- **Right Panel**: Speaker, image choices with labels
- **Game Logic**:
  - ✅ Correct choice: Green highlight + auto next
  - ❌ Wrong (1st try): Red, retry
  - ❌ Wrong (2nd try): All wrongs red, correct flashes green/blue
- **Audio Feedback**: TextToSpeech for question, instruction, and word reading

## 🛠️ Tech Stack
- Kotlin + Jetpack Compose
- MVVM Architecture (ViewModel, StateFlow)
- TextToSpeech
- Android Studio

## 📂 Structure
- `ui/` - Game Composables
- `viewmodel/` - GameViewModel.kt
- `model/` - Question data
- `theme/` - Custom colors & themes

## 👨‍💻 Author
Developed by [Muhammad Ahmad](https://www.linkedin.com/in/muhammad-ahmad-dev/) for Bave Company
