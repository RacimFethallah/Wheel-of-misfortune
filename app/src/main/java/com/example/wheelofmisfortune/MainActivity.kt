package com.example.wheelofmisfortune

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val mySpinner = findViewById<Spinner>(R.id.spinner)
        val myButton = findViewById<Button>(R.id.selection)



        val spinnerData = arrayOf("Groupe 1", "Groupe 2", "Groupe 3", "Groupe 4")
        val spinnerAdapter = ArrayAdapter<String>(this, R.layout.spinner_item, spinnerData)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        mySpinner.adapter = spinnerAdapter



        myButton.setOnClickListener{
            val selectedOption = mySpinner.selectedItem as String
            if (selectedOption == "Groupe 1"){
                val Intent = Intent(this,ActivityG1::class.java)
                startActivity(Intent)
            }else if (selectedOption == "Groupe 2"){
            val Intent = Intent(this,ActivityG2::class.java)
            startActivity(Intent)
            }else if(selectedOption == "Groupe 3"){
                val Intent = Intent(this,ActivityG3::class.java)
                startActivity(Intent)
            }else{
                val Intent = Intent(this,ActivityG4::class.java)
                startActivity(Intent)
            }

        }
    }
}