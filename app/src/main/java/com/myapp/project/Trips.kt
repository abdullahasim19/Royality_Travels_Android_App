package com.myapp.project

import java.io.Serializable
import java.util.*

class Trips:Serializable
{
    public lateinit var location:String
    public var seats:Int = 0
    public lateinit var starttripDate:String
    public lateinit var endtripDate:String
    public var discount:Double=0.0
    public var id:Int=0
    public var price:Int=0
    public
    constructor() {

    }

    constructor(
        id:Int,
        location: String,
        seats: Int,
        starttripDate: String,
        endtripDate: String,
        discount: Double,
        price:Int
    ) {
        this.id=id
        this.location = location
        this.seats = seats
        this.starttripDate = starttripDate
        this.endtripDate = endtripDate
        this.discount = discount
        this.price=price
    }


}
