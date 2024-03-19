package com.example.navigationdrawer

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.navigationdrawer.db.RoomAppDb
import com.example.navigationdrawer.db.UserDao
import com.example.navigationdrawer.db.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class registration : AppCompatActivity() {
    private lateinit var userDao: UserDao

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val registrationButton: Button = findViewById(R.id.registrationBtn)
        val updateButton: Button = findViewById(R.id.updateBtn)
        val editTextName: EditText = findViewById(R.id.editTextName)
        val editTextPhone: EditText = findViewById(R.id.editTextPhone)
        val editTextEmail: EditText = findViewById(R.id.editTextEmail)
        val editTextPassword: EditText = findViewById(R.id.editTextpassword)
        val editTextId : EditText = findViewById(R.id.editTextId)

        val roomAppDb = RoomAppDb.getAppDatabase(this)
        userDao = roomAppDb?.userDao()!!
// update
        val isEditMode = intent.getBooleanExtra("Update_Operation", false)

        if (isEditMode) {
            // If in edit mode, populate the fields with existing data
            val userId = intent.getIntExtra("User_Id", 0)
            val name = intent.getStringExtra("User_Name")
            val phone = intent.getStringExtra("User_Phone")
            val email = intent.getStringExtra("User_Email")
            val password = intent.getStringExtra("User_Password")

            editTextId.setText(userId.toString())  // Set User_Id to editTextId
            editTextName.setText(name)
            editTextPhone.setText(phone)
            editTextEmail.setText(email)
            editTextPassword.setText(password)

            registrationButton.visibility = View.GONE
            updateButton.visibility = View.VISIBLE
        }
        else{
            registrationButton.visibility = View.VISIBLE
            updateButton.visibility = View.GONE

        }

        registrationButton.setOnClickListener {
            // Insert new user
            val id:Int = editTextId.text.toString().toInt()
            val name: String = editTextName.text.toString()
            val phone: String = editTextPhone.text.toString()
            val email: String = editTextEmail.text.toString()
            val password: String = editTextPassword.text.toString()

            val user = UserEntity(name = name, phone = phone, email = email, password = password)

            CoroutineScope(Dispatchers.IO).launch {
                userDao.insertUser(user)

                // After successful registration, start the list view activity
                startActivity(Intent(this@registration, ListviewActivity::class.java))

                showToast("Registered successfully!!!😊")
                finish()
            }
        }
// update
        updateButton.setOnClickListener {
            // Update existing user
            val userId: Int = editTextId.text.toString().toInt()
            val name: String = editTextName.text.toString()
            val phone: String = editTextPhone.text.toString()
            val email: String = editTextEmail.text.toString()
            val password: String = editTextPassword.text.toString()

            val updatedUser = UserEntity(id = userId, name = name, phone = phone, email = email, password = password)

            CoroutineScope(Dispatchers.IO).launch {
                userDao.updateUser(updatedUser)

                // After successful update, start the list view activity
                startActivity(Intent(this@registration, ListviewActivity::class.java))

                showToast("Updated successfully!!!😊")
                finish()
            }
        }
    }
// Update
    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}
