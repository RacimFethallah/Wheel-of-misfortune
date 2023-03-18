package com.example.wheelofmisfortune




import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*



class secondActivity : AppCompatActivity() {



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)



        val wheelView = findViewById<WheelView>(R.id.wheel_view)
        var liste = mutableListOf<String>()
        wheelView.titles = liste

        val addButton = findViewById<Button>(R.id.button)
        val addtext = findViewById<EditText>(R.id.addtext)
        val spinButton = findViewById<Button>(R.id.spinButton)




        spinButton.setOnClickListener {
            wheelView.spinWheel()
        }

        addButton.setOnClickListener {

        }
    }
}




//var text = addtext.text.toString().trim()
//when {
 //   text.isEmpty() -> {
 //       Toast.makeText(this, "Champ vide", Toast.LENGTH_SHORT).show()
 //   }
 //   else -> {
 //       liste.add(text)
 //       wheelView.titles = liste
  //      addtext.setText("")
  //  }

