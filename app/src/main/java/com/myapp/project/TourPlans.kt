package com.myapp.project

class TourPlans
{
    var idTrip:Int=0
    lateinit var locationTrip:String
    lateinit var plan:String

    constructor()
    {

    }

    constructor(idTrip: Int, locationTrip: String, plan: String) {
        this.idTrip = idTrip
        this.locationTrip = locationTrip
        this.plan = plan
    }


}