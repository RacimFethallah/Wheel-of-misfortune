package com.example.wheelofmisfortune




import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat


class secondActivity : AppCompatActivity() {





    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)



        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val spinButton = findViewById<Button>(R.id.spinButton)



        val db = Wheeldb(this, null)
        val wheelId = intent.getLongExtra("wheel_id", -1)
        val currentWheel = db.getWheelId(wheelId)






        val wheelView = findViewById<WheelView>(R.id.wheel_view)
        val liste = mutableListOf<String>()
        wheelView.titles = liste


        val cursor = db.getVal(currentWheel)
        if (cursor != null) {
            while (cursor.moveToNext()) {
                liste.add(cursor.getString(cursor.getColumnIndexOrThrow(Wheeldb.VALUES_NAME)))
            }
            wheelView.titles = liste
        }





        val addval = findViewById<EditText>(R.id.addtextv)
        val deleteval = findViewById<EditText>(R.id.deletetextv)
        val addButton = findViewById<Button>(R.id.addv)
        val deleteButton = findViewById<Button>(R.id.deletev)
        val editButton = findViewById<Button>(R.id.editv)




        var editButtonDrawable: Drawable
        var addButtonDrawable: Drawable
        var deleteButtonDrawable: Drawable
        val plus = ContextCompat.getDrawable(this, R.drawable.plus)
        val edit = ContextCompat.getDrawable(this, R.drawable.edit)
        val cross = ContextCompat.getDrawable(this, R.drawable.cancel)
        val check = ContextCompat.getDrawable(this, R.drawable.check)
        val del = ContextCompat.getDrawable(this, R.drawable.delete)



        //function to spin the wheel
        spinButton.setOnClickListener {
            for ((index, element) in liste.withIndex()) {
                Log.d("MyApp", "Index $index: $element")
            }
            addButton.visibility = View.GONE
            deleteButton.visibility = View.GONE
            editButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.edit, 0, 0)
            wheelView.spinWheel(spinButton)
        }




        //fonction du bouton editer valeur
        editButton.setOnClickListener{
            editButtonDrawable = editButton.compoundDrawables[1]
            when (editButtonDrawable.constantState) {
                edit?.constantState -> {
                    addButton.visibility = View.VISIBLE
                    deleteButton.visibility = View.VISIBLE
                    editButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.cancel, 0, 0)
                }
                cross?.constantState -> {
                    addButton.visibility = View.GONE
                    deleteButton.visibility = View.GONE
                    addval.visibility = View.GONE
                    deleteval.visibility = View.GONE
                    addval.setText("")
                    deleteval.setText("")
                    editButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.edit, 0, 0)
                    addButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.plus, 0, 0)
                    deleteButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.delete, 0, 0)

                }
            }
        }

        //fonction du bouton add valeur
        addButton.setOnClickListener {
            addButtonDrawable = addButton.compoundDrawables[1]
            when(addButtonDrawable.constantState) {
                plus?.constantState -> {
                    deleteButton.visibility = View.GONE
                    addval.visibility = View.VISIBLE
                    addval.requestFocus()
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                    editButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.cancel, 0, 0)
                    addButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.check, 0, 0)
                    addButtonDrawable = addButton.compoundDrawables[1]
                }
                check?.constantState -> {
                    val values = addval.text.toString().trim().split("[\n,]".toRegex())
                    for (value in values) {
                        if (value == "") {
                            Toast.makeText(this, "Champ vide", Toast.LENGTH_SHORT).show()
                        } else if (liste.contains(value)) {
                            Toast.makeText(this, "valeur '$value' existe deja", Toast.LENGTH_SHORT).show()
                        } else {
                            inputMethodManager.hideSoftInputFromWindow(addval.windowToken, 0)
                            liste.add(value)
                            db.addval(value, currentWheel)
                            wheelView.titles = liste
                            addval.setText("")
                            addval.visibility = View.GONE
                            addButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.plus, 0, 0)
                            deleteButton.visibility = View.VISIBLE
                            addButtonDrawable = addButton.compoundDrawables[1]
                        }
                    }

                }
            }
        }


        //fonction du bouton supprimer

        deleteButton.setOnClickListener {
            deleteButtonDrawable = deleteButton.compoundDrawables[1]
            when(deleteButtonDrawable.constantState) {
                del?.constantState -> {
                    addButton.visibility = View.GONE
                    deleteval.visibility = View.VISIBLE
                    deleteval.requestFocus()
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                    editButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.cancel, 0, 0)
                    deleteButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.check, 0, 0)
                    deleteButtonDrawable = deleteButton.compoundDrawables[1]
                }
                check?.constantState -> {
                    when (val text = deleteval.text.toString().trim()) {
                        "" -> {
                            Toast.makeText(this, "Champ vide", Toast.LENGTH_SHORT).show()
                        }
                        !in liste -> {
                            Toast.makeText(this, "valeur '$text' n'existe pas!", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            inputMethodManager.hideSoftInputFromWindow(deleteval.windowToken, 0)
                            liste.remove(text)
                            db.deleteVal(text)
                            wheelView.titles = liste
                            deleteval.setText("")
                            deleteval.visibility = View.GONE
                            deleteButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.delete, 0, 0)
                            addButton.visibility = View.VISIBLE
                            deleteButtonDrawable = addButton.compoundDrawables[1]
                        }
                    }
                }
            }
        }





    }
}






