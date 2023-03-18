package com.example.wheelofmisfortune


import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.sujanpoudel.wheelview.WheelView
import kotlin.random.Random






class secondActivity : AppCompatActivity() {



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)



        val wheelView = findViewById<WheelView>(R.id.wheel_view)
        wheelView.titles = listOf("Bubble Sort", "Quick Sort", "Merge Sort", "Radix Sort")

        val addButton = findViewById<Button>(R.id.button)
        val addtext = findViewById<EditText>(R.id.addtext)




        addButton.setOnClickListener {
            var text = addtext.text.toString().trim()
            when {
                text.isEmpty() -> {
                    Toast.makeText(this, "Champ vide", Toast.LENGTH_SHORT).show()
                }
                else -> {

                    addtext.setText("")


                }

            }


        }
    }






}
