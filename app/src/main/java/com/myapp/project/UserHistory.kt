package com.myapp.project

class UserHistory {
    public lateinit var email:String
    lateinit var tripdetails:Trips
    lateinit var curPackage:String
    public
    constructor() {

    }


    constructor(email: String, tripdetails: Trips, curPackage: String) {
        this.email = email
        this.tripdetails = tripdetails
        this.curPackage = curPackage
    }

}