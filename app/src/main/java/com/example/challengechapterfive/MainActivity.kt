package com.example.challengechapterfive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.challengechapterfive.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    companion object{
        const val SHARED_PREFERENCES = "sharedpreferences"
    }
}