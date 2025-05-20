package com.example.flashcards

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ScoreScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_score_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val exitButton = findViewById<Button>(R.id.button5)
        val feedbackText = findViewById<TextView>(R.id.textView4)
        val scoreText = findViewById<TextView>(R.id.textView5)
        val reviewButton = findViewById<Button>(R.id.button2)
        val feedbackButton = findViewById<Button>(R.id.button7)
        val reviewFeed = findViewById<TextView>(R.id.textView6)

        val score = intent.getIntExtra("score", 0)
        val total = intent.getIntExtra("total", 5)
        val questions = intent.getStringArrayExtra("questions") ?: arrayOf()
        val answers = intent.getBooleanArrayExtra("answers") ?: booleanArrayOf()

        scoreText.text = getString(R.string.your_score, score)

        feedbackText.text = if (score >= 3) {
            "Great job!"
        } else {
            "Keep practising!"
        }

        reviewButton.setOnClickListener {
            if (questions.isNotEmpty() && answers.isNotEmpty()) {
                val reviewOutput = buildString {
                    for (i in questions.indices) {
                        val correctAnswer = if (i < answers.size && answers[i]) "True" else "False"
                        append("${i + 1}. ${questions[i]}\nCorrect Answer: $correctAnswer\n\n")
                    }
                }
                reviewFeed.text = reviewOutput.trim()
            } else {
                reviewFeed.text = "No questions available to review."
            }
        }

        feedbackButton.setOnClickListener {
            feedbackText.text = when {
                score == total -> "Perfect! You really know your stuff!"
                score >= 3 -> "Great job! You're doing well!"
                score == 2 -> "Not bad, but keep practicing!"
                else -> "Don't give up! Study a bit more and try again."
            }
        }

        exitButton.setOnClickListener {
            finishAffinity()
        }
    }
}