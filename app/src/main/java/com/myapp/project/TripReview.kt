package com.myapp.project

class TripReview {

    public lateinit var email:String
    public var idofthetrip:Int=0
    public lateinit var locationofthetrip:String
    public lateinit var reviewGiven:String
    public var ratingofTrip:Double=0.0
    constructor()
    {

    }

    constructor(
        email: String,
        idofthetrip: Int,
        locationofthetrip: String,
        reviewGiven: String,
        ratingofTrip: Double
    ) {
        this.email = email
        this.idofthetrip = idofthetrip
        this.locationofthetrip = locationofthetrip
        this.reviewGiven = reviewGiven
        this.ratingofTrip = ratingofTrip
    }
}