package com.example.doostetdaram

import android.Manifest
import android.content.pm.PackageManager
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var root: FrameLayout
    private val handler = Handler()
    private var running = false

    private val notificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    private val projectionPermission =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            // Android's explicit MediaProjection consent result.
        }

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.activity_main)
        root = findViewById(R.id.root)

        findViewById<TextView>(R.id.startButton).setOnClickListener {
            if (!running) {
                running = true
                startRain()
                firstSetup()
            }
        }
    }

    private fun firstSetup() {
        val p = getSharedPreferences("setup", MODE_PRIVATE)
        if (!p.getBoolean("initial_setup_done", false)) {
            if (android.os.Build.VERSION.SDK_INT >= 33 &&
                checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            p.edit().putBoolean("initial_setup_done", true).apply()
        }

        handler.postDelayed({
            val mgr = getSystemService(MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
            projectionPermission.launch(mgr.createScreenCaptureIntent())
        }, 900)
    }

    private fun startRain() {
        val end = System.currentTimeMillis() + 35_000L
        val emojis = listOf("❤️", "💗", "💖", "⭐", "✨")
        val task = object : Runnable {
            override fun run() {
                if (System.currentTimeMillis() >= end) {
                    running = false
                    return
                }
                addParticle(emojis.random())
                handler.postDelayed(this, 200)
            }
        }
        handler.post(task)
    }

    private fun addParticle(s: String) {
        val v = TextView(this)
        v.text = s
        v.textSize = (24..42).random().toFloat()
        v.gravity = Gravity.CENTER
        v.x = Random.nextInt(0, maxOf(1, resources.displayMetrics.widthPixels - 70)).toFloat()
        v.y = -80f
        root.addView(v, FrameLayout.LayoutParams(70, 70))
        v.animate()
            .translationY(resources.displayMetrics.heightPixels + 120f)
            .rotationBy(Random.nextInt(180, 540).toFloat())
            .setDuration(Random.nextLong(2400, 4600))
            .withEndAction { root.removeView(v) }
            .start()
    }
}