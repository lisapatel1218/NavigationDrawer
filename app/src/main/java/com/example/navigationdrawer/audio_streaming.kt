package com.example.navigationdrawer

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView

class audio_streaming : AppCompatActivity() {
    private lateinit var playBtn: ImageButton
    private lateinit var forwardBtn: ImageButton
    private lateinit var replayBtn: ImageButton
    private lateinit var seekBar: SeekBar
    private lateinit var tvCurrentTime: TextView
    private lateinit var tvTotalDuration: TextView
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var runnable: Runnable
    private lateinit var likeBtn: ImageButton
    private lateinit var dislikeBtn: ImageButton

    private var isLiked = false
    private var isDisliked = false
    private var handler = Handler()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_streaming)

        playBtn = findViewById(R.id.play_btn)
        forwardBtn = findViewById(R.id.forward_btn)
        replayBtn = findViewById(R.id.replay_btn)
        seekBar = findViewById(R.id.seekbar)
        tvCurrentTime = findViewById(R.id.tvCurrentTime)
        tvTotalDuration = findViewById(R.id.tvTotalDuration)
        likeBtn = findViewById(R.id.like_btn)
        dislikeBtn = findViewById(R.id.dislike_btn)

        mediaPlayer = MediaPlayer.create(this, R.raw.audiostreming)
        seekBar.progress = 0
        seekBar.max = mediaPlayer.duration

        val skipAmount = 10000



        likeBtn.setOnClickListener {
            // Toggle like state and update UI
            toggleLikeState()
        }

        dislikeBtn.setOnClickListener {
            // Toggle dislike state and update UI
            toggleDislikeState()
        }


        playBtn.setOnClickListener {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                playBtn.setImageResource(R.drawable.baseline_pause_24)
            } else {
                mediaPlayer.pause()
                playBtn.setImageResource(R.drawable.baseline_play_arrow_24)
            }
        }

        forwardBtn.setOnClickListener {
            val newTime = mediaPlayer.currentPosition + skipAmount
            mediaPlayer.seekTo(newTime.coerceAtMost(mediaPlayer.duration))
        }

        replayBtn.setOnClickListener {
            val newTime = mediaPlayer.currentPosition - skipAmount
            mediaPlayer.seekTo(newTime.coerceAtLeast(0))
        }

        tvTotalDuration.text = formatTime(mediaPlayer.duration)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, pos: Int, changed: Boolean) {
                if (changed) {
                    mediaPlayer.seekTo(pos)
                    tvCurrentTime.text = formatTime(pos)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        runnable = Runnable {
            seekBar.progress = mediaPlayer.currentPosition
            tvCurrentTime.text = formatTime(mediaPlayer.currentPosition)
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)

        mediaPlayer.setOnCompletionListener {
            playBtn.setImageResource(R.drawable.baseline_play_arrow_24)
            seekBar.progress = 0
        }
    }

    private fun toggleLikeState() {
        if (!isLiked) {
            isLiked = true
            likeBtn.setImageResource(R.drawable.baseline_thumb_like1)

            if (isDisliked) {
                isDisliked = false
                dislikeBtn.setImageResource(R.drawable.baseline_thumb_idislike)
            }
        } else {
            isLiked = false
            likeBtn.setImageResource(R.drawable.baseline_thumb_like1)
        }
    }

    private fun toggleDislikeState() {
        if (!isDisliked) {
            isDisliked = true
            dislikeBtn.setImageResource(R.drawable.baseline_thumb_idislike)

            if (isLiked) {
                isLiked = false
                likeBtn.setImageResource(R.drawable.baseline_thumb_like1)
            }
        } else {
            isDisliked = false
            dislikeBtn.setImageResource(R.drawable.baseline_thumb_idislike)
        }
    }

    private fun formatTime(milliseconds: Int): String {
        val minutes = milliseconds / 1000 / 60
        val seconds = milliseconds / 1000 % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacks(runnable)
    }
}



