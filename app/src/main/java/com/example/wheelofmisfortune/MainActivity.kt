package com.example.wheelofmisfortune

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val mySpinner = findViewById<Spinner>(R.id.spinner)
        val myButton = findViewById<Button>(R.id.selection)



        val spinnerData = arrayOf("Groupe 1", "Groupe 2", "Groupe 3", "Groupe 4")
        val spinnerAdapter = ArrayAdapter(this, R.layout.spinner_item, spinnerData)
        mySpinner.adapter = spinnerAdapter



        myButton.setOnClickListener{
            when (mySpinner.selectedItem as String){
                "Groupe 1" -> {val intent = Intent(this,ActivityG1::class.java)
                    startActivity(intent)}
                "Groupe 2" -> {val intent = Intent(this,ActivityG2::class.java)
                    startActivity(intent)}
                "Groupe 3" -> {val intent = Intent(this,ActivityG3::class.java)
                    startActivity(intent)}
                "Groupe 4" -> {val intent = Intent(this,ActivityG4::class.java)
                    startActivity(intent)}
            }

        }
    }
}