package com.example.kontrolem

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.activity_main)
        val live = findViewById<FrameLayout>(R.id.liveContainer)
        live.post {
            val w = live.width
            live.layoutParams = live.layoutParams.apply { height = w * 16 / 9 }
            live.requestLayout()
        }
    }
}