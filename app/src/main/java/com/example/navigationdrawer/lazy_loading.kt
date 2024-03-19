package com.example.navigationdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.os.Handler
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationdrawer.databinding.ActivityLazyLoadingBinding


class lazy_loading : AppCompatActivity() {
    private val numberList: MutableList<String> = ArrayList()
    private var page = 1
    private var isLoading = false
    private val limit = 20

    private lateinit var adapter: NumberAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var binding: ActivityLazyLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityLazyLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        adapter = NumberAdapter(this)
        binding.recyclerView.adapter = adapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    page++
                    getPage()
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

        getPage()
    }

    private fun getPage() {
        isLoading = true
        binding.progressBar.visibility = View.VISIBLE
        val start = (page - 1) * limit
        val end = (page) * limit

       Handler().postDelayed({
            val newItems = mutableListOf<String>()
            for (i in start until end) {
                newItems.add("Item $i")
            }

            // Add new items to the existing list
            adapter.addItems(newItems)

            // Notify the adapter that items are inserted
            adapter.notifyItemRangeInserted(adapter.itemCount - newItems.size, newItems.size)

            // Update the UI
            binding.progressBar.visibility = View.GONE
            isLoading = false
        }, 5000)
    }


    // Calculate the difference between two lists using DiffUtil
    private fun calculateDiff(oldList: List<String>, newList: List<String>): DiffUtil.DiffResult {
        val callback = MyDiffCallback(oldList, newList)
        return DiffUtil.calculateDiff(callback)
    }

    // Custom DiffUtil.Callback implementation
    class MyDiffCallback(private val oldList: List<String>, private val newList: List<String>) :
        DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

    // NumberAdapter with updated methods
    class NumberAdapter(private val activity: lazy_loading) :
        RecyclerView.Adapter<NumberAdapter.NumberViewHolder>() {

        private var data: List<String> = mutableListOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
            return NumberViewHolder(
                LayoutInflater.from(activity).inflate(
                    R.layout.rv_child,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
            holder.tvNumber.text = data[position]
        }

        fun setData(newData: List<String>) {
            data = newData
        }

        fun getData(): List<String> {
            return data
        }

        class NumberViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val tvNumber = v.findViewById<TextView>(R.id.tv_number)
        }
        fun addItems(newItems: List<String>) {
            val updatedList = mutableListOf<String>()
            updatedList.addAll(data)
            updatedList.addAll(newItems)
            data = updatedList
        }

    }

    }
