package com.example.navigationdrawer


import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationdrawer.db.RoomAppDb
import com.example.navigationdrawer.db.UserDao
import com.example.navigationdrawer.db.UserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class ListviewActivity : AppCompatActivity() {

    private lateinit var userDao: UserDao
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserlistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listview)

        val roomAppDb = RoomAppDb.getAppDatabase(this)
        userDao = roomAppDb?.userDao()!!
        recyclerView = findViewById(R.id.recyclerViewUsers)
        userAdapter = UserlistAdapter(
            emptyList(), this,
            onEditClickListener = { Users ->
                startUpdateActivity(Users)
            },
            onDeleteClickListener = { Users ->
                showDeleteConfirmationDialog(Users)
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter

        fetchDataAndPopulateRecyclerView()

        setupSearchView()
        setupSwipeHandler()
    }

    private fun setupSearchView() {
        val searchView = findViewById<SearchView>(R.id.searchViewUsers)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                userAdapter.filter.filter(newText)
                return true
            }
        })
    }
    private fun setupSwipeHandler() {
        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val user = userAdapter.filteredUserList[position]

                if (direction == ItemTouchHelper.LEFT) {
                    AlertDialog.Builder(this@ListviewActivity)
                        .setTitle("Confirm Deletion")
                        .setMessage("Are you sure you want to delete this record?")
                        .setPositiveButton("Yes") { _, _ ->
                            deleteUser(user)
                        }
                        .setNegativeButton("No") { dialog, _ ->
                            dialog.dismiss()
                            userAdapter.notifyItemChanged(position)
                        }
                        .show()
                } else if (direction == ItemTouchHelper.RIGHT) {
                    // Show an alert dialog for the update operation
                    AlertDialog.Builder(this@ListviewActivity)
                        .setTitle("Confirm Update")
                        .setMessage("Do you want to update this record?")
                        .setPositiveButton("Yes") { _, _ ->
                            startUpdateActivity(user)
                        }
                        .setNegativeButton("No") { dialog, _ ->
                            dialog.dismiss()
                            userAdapter.notifyItemChanged(position) // Reset the view if the user cancels the update operation
                        }
                        .show()
                }
            }

            override fun onChildDraw(canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                     dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                RecyclerViewSwipeDecorator.Builder(this@ListviewActivity, canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@ListviewActivity, R.color.swipeDeleteBackground))
                    .addSwipeLeftActionIcon(R.drawable.baseline_delete_24)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(this@ListviewActivity, R.color.swipeUpdateBackground))
                    .addSwipeRightActionIcon(R.drawable.baseline_edit_241)
                    .create()
                    .decorate()
                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun fetchDataAndPopulateRecyclerView() {
        CoroutineScope(Dispatchers.IO).launch {
            val userList = userDao.getAllUsers()
            withContext(Dispatchers.Main) {
                userAdapter.updateData(userList)
            }
        }
    }

    private fun startUpdateActivity(user: UserEntity) {
        // Implement your update logic here
        val intent = Intent(this, registration::class.java).apply {
            putExtra("User_Id", user.id)
            putExtra("User_Name", user.name)
            putExtra("User_Phone", user.phone)
            putExtra("User_Email", user.email)
            putExtra("User_Pass", user.password)
            putExtra("Update_Operation", true)
        }
        startActivity(intent)
    }

    private fun showDeleteConfirmationDialog(user: UserEntity) {
        AlertDialog.Builder(this)
            .setTitle("Confirm Deletion")
            .setMessage("Are you sure you want to delete this record?")
            .setPositiveButton("Yes") { _, _ ->
                deleteUser(user)
            }
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.dismiss()
                fetchDataAndPopulateRecyclerView()
            }
            .show()
    }

    private fun deleteUser(user: UserEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.deleteUser(user)
            fetchDataAndPopulateRecyclerView()
        }
    }
}
