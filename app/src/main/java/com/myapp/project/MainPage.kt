package com.myapp.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainPage : AppCompatActivity() {
    lateinit var name:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        name=findViewById<TextView>(R.id.textView)
        val i=intent.getStringExtra("email")
        name.text=i
    }
}