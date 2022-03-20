package com.razit.capstone1.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.razit.capstone1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityHomeBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)
        supportActionBar?.hide()
        supportActionBar?.elevation = 0f
    }
}