package com.example.flashcards

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FlashScreen : AppCompatActivity() {

    private val questions = arrayOf(
        "Nelson Mandela was the president in 1994",
        "The Cold War ended in 1990",
        "Julius Caesar was a Roman Emperor",
        "The Berlin Wall fell in 1989",
        "World War II ended in 1945"
    )

    private val answers = arrayOf(true, true, false, true, true)

    private var currentIndex = 0
    private var score = 0
    private var answered = false

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var questionText: TextView
    private lateinit var scoreText: TextView
    private lateinit var nextButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_flash_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        scoreText = findViewById(R.id.textView3)
        questionText = findViewById(R.id.textView2)
        trueButton = findViewById(R.id.button8)
        falseButton = findViewById(R.id.button1)
        nextButton = findViewById(R.id.button3)
        val homeButton = findViewById<Button>(R.id.button4)

        displayQuestion()

        questionText.text = questions[currentIndex]

        trueButton.setOnClickListener {
            if (!answered) checkAnswer(true)
        }

        falseButton.setOnClickListener {
            if (!answered) checkAnswer(true)
        }

        homeButton.setOnClickListener {
            startActivity(Intent(this@FlashScreen, MainActivity::class.java))
            finish()
        }

        nextButton.setOnClickListener {
            if (currentIndex < questions.size - 1) {
                currentIndex++
                displayQuestion() // Use displayQuestion() to reset everything
            } else {
                goToScoreScreen()
            }
        }

    }

    private fun displayQuestion() {
        questionText.text = questions[currentIndex]
        scoreText.text = getString(R.string.your_score, score)
        setAnswerButtonsEnabled(true)
        answered = false
    }

    private fun checkAnswer(userAnswer: Boolean) {
        answered = true
        val correctAnswer = answers[currentIndex]
        if (userAnswer == correctAnswer) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            score++
        } else {
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
        }

        setAnswerButtonsEnabled(false)
        scoreText.text = getString(R.string.your_score, score)
    }

    private fun setAnswerButtonsEnabled(enabled: Boolean) {
        trueButton.isEnabled = enabled
        falseButton.isEnabled = enabled
    }

    private fun goToScoreScreen() {
        val intent = Intent(this@FlashScreen, ScoreScreen::class.java)
        intent.putExtra("score", score)
        intent.putExtra("total", questions.size)
        intent.putExtra("questions", questions)
        intent.putExtra("answers", answers.toBooleanArray())
        startActivity(intent)
        finish()
    }

}

