package com.example.navigationdrawer

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.navigationdrawer.db.RoomAppDb
import com.example.navigationdrawer.db.UserDao
import com.example.navigationdrawer.db.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel(app: Application) : AndroidViewModel(app) {

    val allUsers: MutableLiveData<List<UserEntity>> = MutableLiveData()
    private val userDao: UserDao? = RoomAppDb.getAppDatabase(app)?.userDao()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // Perform the database operation in the background thread
                val users = userDao?.getAllUsers()

                // Switch back to the main thread to update LiveData
                withContext(Dispatchers.Main) {
                    allUsers.value = users
                }
            }
        }
    }

    fun insertUserInfo(entity: UserEntity) {
        viewModelScope.launch {
            userDao?.insertUser(entity)
            fetchUsers()
        }
    }

    fun deleteUser(user: UserEntity) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // Perform the database operation in the background thread
                userDao?.deleteUser(user)
            }
            fetchUsers() // Refresh the LiveData after the operation is completed
        }
    }
}
