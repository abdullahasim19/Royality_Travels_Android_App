package com.myapp.project

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth

class MainPage : AppCompatActivity() {
    lateinit var name:TextView
    lateinit var userData:User
    lateinit var title:TextView
    lateinit var adView:AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        adView=findViewById<AdView>(R.id.adView)

        MobileAds.initialize(this)
        var adRequest=AdRequest.Builder().build()
        adView.loadAd(adRequest)

        title=findViewById<TextView>(R.id.welcometext)

        try {
            userData=intent.getSerializableExtra("UserInfo") as User
        }
        catch (e:Exception)
        {
            Toast.makeText(this,e.message.toString(),Toast.LENGTH_SHORT).show()
        }


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

        val exB=findViewById<Button>(R.id.btnExplore)

        exB.setOnClickListener {
            val i=Intent(this,ExplorePage::class.java)
            startActivity(i)
        }

        val edits=findViewById<Button>(R.id.btnEdit)
        edits.setOnClickListener {
            val i=Intent(this,EditProfile::class.java)
            i.putExtra("UserInfo",userData)
            startActivityForResult(i,1)
        }

        val info=findViewById<Button>(R.id.packageInfo)
        info.setOnClickListener {
            val i=Intent(this,PackageInfo::class.java)
            startActivity(i)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK)
        {
            if(requestCode==1)
            {
                if(data!=null) {
                    userData = data!!.getSerializableExtra("UserInfoUpdated") as User
                    if(title.text!=userData.userNameTrip)
                    {
                        title.text="Welcome "
                        title.text=title.text.toString().plus(userData.userNameTrip)
                    }
                }
            }
        }
    }
}