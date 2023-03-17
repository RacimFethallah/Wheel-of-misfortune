package com.example.wheelofmisfortune

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import kotlin.random.Random

class ActivityG4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_g4)

        val btnSpin = findViewById<Button>(R.id.spin)
        val btnSpin2 = findViewById<Button>(R.id.spin2)
        val wheel = findViewById<ImageView>(R.id.wheelg4)

        fun spin(){
            btnSpin.isEnabled = false
            btnSpin2.isEnabled = false
            var spin: Int = Random.nextInt(20) + 10
            spin *= 14
            val timer = object: CountDownTimer((spin*20).toLong(), 1) {
                override fun onTick(millisUntilFinished: Long) {
                    val rotation: Float = wheel.rotation + 3
                    wheel.rotation = rotation

                }

                override fun onFinish() {
                    btnSpin.isEnabled = true
                    btnSpin2.isEnabled = true
                }
            }
            timer.start()
        }
        btnSpin.setOnClickListener{spin()}
        btnSpin2.setOnClickListener{spin()}

    }
}