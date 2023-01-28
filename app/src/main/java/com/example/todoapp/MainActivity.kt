package com.example.todoapp

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        android.os.Handler(Looper.getMainLooper()).postDelayed(
            {
                val intent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(intent)
            },
            2000
        )
    }
}