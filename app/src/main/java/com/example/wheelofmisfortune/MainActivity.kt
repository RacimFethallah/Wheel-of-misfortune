package com.example.wheelofmisfortune

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)







        val mySpinner = findViewById<Spinner>(R.id.spinner)
        val myButton = findViewById<Button>(R.id.selection)
        val editSpinner = findViewById<Button>(R.id.edit)
        val addButton = findViewById<Button>(R.id.add)
        val deleteButton = findViewById<Button>(R.id.delete)
        val addtext = findViewById<EditText>(R.id.addtext)
        val addtext2 = findViewById<EditText>(R.id.addtext2)
        var text: String


        var addButtonDrawable: Drawable
        var editButtonDrawable: Drawable
        val plus = ContextCompat.getDrawable(this, R.drawable.plus)
        val edit = ContextCompat.getDrawable(this, R.drawable.edit)
        val cross = ContextCompat.getDrawable(this, R.drawable.cancel)


        val spinnerData = mutableListOf<String>()
        val spinnerAdapter = ArrayAdapter(this, R.layout.spinner_item, spinnerData)
        mySpinner.adapter = spinnerAdapter




        val db = Wheeldb(this, null)



        val cursor = db.getNamedb()

        if (cursor != null) {
            while (cursor.moveToNext()) {
                spinnerAdapter.add(cursor.getString(cursor.getColumnIndexOrThrow(Wheeldb.COLUMN_NAME)))
            }
        }






        //verifie si le spinner est vide
        if (spinnerAdapter.count != 0) {  //Si spinner non vide on ne peut qu'editer
            editSpinner.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.edit, 0, 0)
        } else { //Si spinner vide on ne peut qu'ajouter
            editSpinner.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.plus, 0, 0)
        }







        // Fonction du bouton editer roue
        editSpinner.setOnClickListener {
            editButtonDrawable = editSpinner.compoundDrawables[1]
            when (editButtonDrawable.constantState) {
                edit?.constantState -> {
                    editSpinner.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.cancel, 0, 0)
                    addButton.visibility = View.VISIBLE
                    deleteButton.visibility = View.VISIBLE
                }
                cross?.constantState -> {
                    addButton.visibility = View.GONE
                    deleteButton.visibility = View.GONE
                    addtext2.visibility = View.GONE
                    addtext2.setText("")
                    addButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.plus, 0, 0)
                    editSpinner.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.edit, 0, 0)
                }
                plus?.constantState -> {
                    addtext.visibility = View.VISIBLE
                    editSpinner.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.check, 0, 0)
                }
                else -> {
                    text = addtext.text.toString().trim()
                    when {
                        text.isEmpty() -> {
                            Toast.makeText(this, "Champ vide", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            addtext.visibility = View.GONE
                            editSpinner.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.edit, 0, 0)
                            spinnerAdapter.add(text)
                            addtext.setText("")
                            db.addWheeldb(text)
                        }
                    }
                }
            }
        }






        //Fonction du bouton ajouter roue

        addButton.setOnClickListener {
            addButtonDrawable = addButton.compoundDrawables[1]
            if (addButtonDrawable.constantState == plus?.constantState) {
                addButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.check, 0, 0)
                addtext2.visibility = View.VISIBLE
                deleteButton.visibility = View.GONE
                addButtonDrawable = addButton.compoundDrawables[1]
            } else {
                text = addtext2.text.toString().trim()
                if (text == "") {
                    Toast.makeText(this, "Champ vide", Toast.LENGTH_SHORT).show()
                } else if (spinnerData.contains(text)) {
                    Toast.makeText(this, "Roue '$text' existe deja", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    db.addWheeldb(text)
                    spinnerAdapter.add(text)
                    addtext2.visibility = View.GONE
                    addButton.visibility = View.GONE
                    addtext2.setText("")
                    editSpinner.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.edit, 0, 0)
                    addButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.plus, 0, 0)
                    addButtonDrawable = addButton.compoundDrawables[1]
                }
            }
        }





        //Fonction du bouton supprimer roue
        deleteButton.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Supprimer Roue")
            builder.setMessage("Voulez vous supprimer la roue selectionnée?")
            builder.setPositiveButton("Oui") { _, _ ->
                val selectedItem = mySpinner.selectedItem as String
                spinnerAdapter.remove(selectedItem)
                db.deleteWheel(selectedItem)
                addButton.visibility = View.GONE
                deleteButton.visibility = View.GONE
                editSpinner.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.edit, 0, 0)
                editButtonDrawable = editSpinner.compoundDrawables[1]
                if(spinnerAdapter.count == 0){editSpinner.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.plus, 0, 0)
                    editButtonDrawable = editSpinner.compoundDrawables[1]}
            }
            builder.setNegativeButton("Annuler") { _, _ ->
                // ne rien faire
            }
            val dialog = builder.create()
            dialog.show()
        }



        //Fonction du bouton selection de roue

        myButton.setOnClickListener {

            if (spinnerAdapter.count > 0) {
                val i = Intent(this, secondActivity::class.java)
                val spinnerResult = mySpinner.selectedItem.toString()
                i.putExtra("spinnerResult", spinnerResult)
                startActivity(i)
            } else {
                Toast.makeText(this, "Aucune Roue à selectionner", Toast.LENGTH_SHORT).show()
            }
        }

    }
}