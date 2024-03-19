package com.example.navigationdrawer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationdrawer.R
import com.example.navigationdrawer.db.UserEntity

class UserlistAdapter(
    var userList: List<UserEntity>,
    private val context: Context,
    private val onEditClickListener: (UserEntity) -> Unit,
    private val onDeleteClickListener: (UserEntity) -> Unit
) : RecyclerView.Adapter<UserlistAdapter.UserViewHolder>(), Filterable {

    var filteredUserList: ArrayList<UserEntity> = ArrayList(userList)

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.editTextId)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val phoneTextView: TextView = itemView.findViewById(R.id.phoneTextView)
        val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.my_list_item, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = filteredUserList[position]

        holder.idTextView.text = "ID: ${currentUser.id}"
        holder.nameTextView.text = "Name: ${currentUser.name}"
        holder.phoneTextView.text = "Phone: ${currentUser.phone}"
        holder.emailTextView.text = "Email: ${currentUser.email}"

        holder.itemView.setOnClickListener {
            onEditClickListener.invoke(currentUser)
        }

        holder.itemView.setOnLongClickListener {
            onDeleteClickListener.invoke(currentUser)
            true
        }
    }

    override fun getItemCount(): Int = filteredUserList.size

    fun updateData(newList: List<UserEntity>) {
        filteredUserList.clear()
        filteredUserList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                filteredUserList = if (charString.isEmpty()) {
                    ArrayList(userList)
                } else {
                    val filteredList = ArrayList<UserEntity>()
                    userList
                        .filter {
                            it.name.contains(charString, ignoreCase = true)
                        }
                        .forEach { filteredList.add(it) }
                    filteredList
                }

                return FilterResults().apply { values = filteredUserList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredUserList = results?.values as ArrayList<UserEntity>
                notifyDataSetChanged()
            }
        }
    }
}
