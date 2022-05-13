package com.myapp.project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class BookingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)
        val data=intent.getSerializableExtra("trips") as Trips

        val location=findViewById<TextView>(R.id.lname)
        val price=findViewById<TextView>(R.id.lprice)
        val seats=findViewById<TextView>(R.id.lseats)
        val start=findViewById<TextView>(R.id.lstart)
        val end=findViewById<TextView>(R.id.lend)
        val dis=findViewById<TextView>(R.id.ldiscount)

        location.text=location.text.toString().plus( data.location)
        price.text=price.text.toString().plus(data.price.toString())
        seats.text=seats.text.toString().plus(data.seats.toString())
        start.text=start.text.toString().plus(data.starttripDate)
        end.text=end.text.toString().plus(data.endtripDate)
        dis.text=dis.text.toString().plus(data.discount)
    }
}