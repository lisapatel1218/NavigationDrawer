package com.example.navigationdrawer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FeedAdapter (
    private var posts: List<post>,
    private val onLikeClicked: (post) -> Unit,
    private val onCommentButtonClicked: (post, String) -> Unit
) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {

    class FeedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userNameTextView: TextView = view.findViewById(R.id.userNameTextView)
        val timeAgoTextView: TextView = view.findViewById(R.id.timeAgoTextView)
        val postImageView: ImageView = view.findViewById(R.id.postImageView)
        val likeButton: ImageButton = view.findViewById(R.id.likeButton)
        val likesCountTextView: TextView = view.findViewById(R.id.likesCountTextView)
        val commentButton: ImageButton = view.findViewById(R.id.CommentButton) // Ensure this matches your layout
        val commentCountTextView: TextView = view.findViewById(R.id.commentCountTextView) // Ensure this TextView is in your layout for displaying comment count
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val post = posts[position]
        // Setup post details
        holder.userNameTextView.text = post.userName
        holder.timeAgoTextView.text = post.timeAgo
        holder.likesCountTextView.text = "${post.likesCount} likes"
        holder.commentCountTextView.text = "${post.commentsCount} comments"

        val imageResId = holder.itemView.context.resources.getIdentifier(
            post.postImage.toString(), "drawable", holder.itemView.context.packageName)
        if (imageResId != 0) {
            holder.postImageView.setImageResource(imageResId)
        } else {
            // Set a default image if the resource ID is not found
            //  holder.postImageView.setImageResource(R.drawable.placeholder) // Assume placeholder is a valid drawable
        }

        // Like button setup
        holder.likeButton.setImageResource(if (post.isLiked) R.drawable.baseline_thumb_like1 else R.drawable.baseline_thumb_like1)
        holder.likeButton.setOnClickListener { onLikeClicked(post) }

        // Comment button setup
        holder.commentButton.setOnClickListener {
            // Open dialog to add comment
            showCommentInputDialog(holder.itemView.context, post) { comment ->
                // This callback is triggered with the new comment
                post.comments.add(comment) // Add comment to the list
                onCommentButtonClicked(post, comment) // Inform ViewModel about the new comment
                notifyItemChanged(position) // Refresh item to display new comment count or content
            }
        }
    }

    private fun showCommentInputDialog(context: Context, post: post, onCommentAdded: (String) -> Unit) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_comments, null)
        val commentInput = dialogView.findViewById<EditText>(R.id.commentInput)
        val postCommentButton = dialogView.findViewById<Button>(R.id.postCommentButton)

        // Setup RecyclerView for comments inside the dialog
        val commentsRecyclerView = dialogView.findViewById<RecyclerView>(R.id.commentsRecyclerView)
        commentsRecyclerView.layoutManager = LinearLayoutManager(context)
        val commentsAdapter = CommentsAdapter(post.comments) // Assuming you have a list of comments in the post object
        commentsRecyclerView.adapter = commentsAdapter

        val dialog = AlertDialog.Builder(context)
            .setTitle("Comments")
            .setView(dialogView)
            .setNegativeButton("Cancel", null)
            .create()

        postCommentButton.setOnClickListener {
            val comment = commentInput.text.toString().trim()
            if (comment.isNotEmpty()) {
                // Here, just call onCommentAdded and let the callback handle adding the comment to the list.
                // This ensures the comment is added only once.
                onCommentAdded(comment)
                commentInput.setText("") // Clear the input field after adding the comment
            }
        }

        dialog.show()
    }



    override fun getItemCount(): Int = posts.size

    fun updatePosts(posts: List<post>) {
        this.posts = posts
        notifyDataSetChanged()
    }
}