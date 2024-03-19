package com.example.navigationdrawer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Toast

class formcontrols : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formcontrols)
        val email: EditText = findViewById(R.id.email)
        val Password: EditText = findViewById(R.id.password)
        val male: RadioButton = findViewById(R.id.male)
        val female: RadioButton = findViewById(R.id.Female)
        val dancing: CheckBox = findViewById(R.id.dancing)
        val singing: CheckBox = findViewById(R.id.singing)
        val coding: CheckBox = findViewById(R.id.coding)
        val addBtn: Button = findViewById(R.id.addBtn)

        val items = arrayOf("Select your Country","India","Canada","Australia","USA","Brazil","United kingdom","France","Italy","Pakistan","Europe","New Zealand")

        val spinner = findViewById<Spinner>(R.id.spinner)
        val arrayAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,items)
        spinner.adapter=arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                // Check if the selected position is not the hint position
                if (p2 != 0) {
                    // Set the selected item as the default text in the spinner
                    spinner.setSelection(p2)

                    Toast.makeText(applicationContext, "Selected Country is = " + items[p2], Toast.LENGTH_SHORT).show()
                }
            }


            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        addBtn.setOnClickListener {

            val email1: String = email.text.toString()
            val password: String = Password.text.toString()
//            val confirmPassword: String = Password.text.toString()
            val gender: String = if (male.isChecked) "Male" else "Female"
            val hobbies: StringBuilder = StringBuilder()

            if (dancing.isChecked) hobbies.append("dancing, ")
            if (singing.isChecked) hobbies.append("singing, ")
            if (coding.isChecked) hobbies.append("coding, ")

            val selectedHobbies: String = hobbies.toString().trim { it == ',' }


            if (email1.isEmpty()) {
                email.error = "Email cannot be empty"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
                email.error = "Invalid email"
            }

            if (password.isEmpty()) {
                Password.error = "Password cannot be empty"
            }



            if (!male.isChecked && !female.isChecked) {
                Toast.makeText(applicationContext,"Please select a gender", Toast.LENGTH_SHORT).show()
            }

            if (selectedHobbies.isEmpty()) {
                Toast.makeText(applicationContext,"Please select a hobbie", Toast.LENGTH_SHORT).show()
            }

            Toast.makeText(applicationContext,"Registration Sucessfully !!", Toast.LENGTH_SHORT).show()



        }




    }

}