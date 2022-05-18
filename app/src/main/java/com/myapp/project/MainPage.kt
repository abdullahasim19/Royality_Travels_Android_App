package com.myapp.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainPage : AppCompatActivity() {
    lateinit var name:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)
        lateinit var userData:User

        try {
            userData=intent.getSerializableExtra("UserInfo") as User
        }
        catch (e:Exception)
        {
            Toast.makeText(this,e.message.toString(),Toast.LENGTH_SHORT).show()
        }

        var title=findViewById<TextView>(R.id.welcometext)
        title.text=title.text.toString().plus(userData.userNameTrip)


        val tripButton=findViewById<Button>(R.id.viewtrips)
        tripButton.setOnClickListener {
            val i = Intent(this,ShowTrips::class.java)
            i.putExtra("UserInfo",userData)
            startActivity(i)
        }

        val histButton=findViewById<Button>(R.id.viewHistory)
        histButton.setOnClickListener {
            val i =Intent(this,ShowHistory::class.java)
            i.putExtra("UserInfo",userData)
            startActivity(i)
        }

        val wishButton=findViewById<Button>(R.id.viewWishlist)
        wishButton.setOnClickListener {
            val i =Intent(this,UserWishlist::class.java)
            i.putExtra("UserInfo",userData)
            startActivity(i)
        }


        val tourPlanButton=findViewById<Button>(R.id.viewTourPlan)
        tourPlanButton.setOnClickListener {
            val i=Intent(this,ViewTourPlans::class.java)
            startActivity(i)
        }


        val signOutButton=findViewById<Button>(R.id.signOut)

        signOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            finish()

        }
    }
}