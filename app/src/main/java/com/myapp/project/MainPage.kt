package com.myapp.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class MainPage : AppCompatActivity() {
    lateinit var name:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        var userData=intent.getSerializableExtra("UserInfo") as User
        var title=findViewById<TextView>(R.id.welcometext)
        title.text=title.text.toString().plus(userData.userNameTrip)


        val tripButton=findViewById<Button>(R.id.viewtrips)
        tripButton.setOnClickListener {
            val i = Intent(this,ShowTrips::class.java)
            i.putExtra("UserInfo",userData)
            startActivity(i)
        }

    }
}