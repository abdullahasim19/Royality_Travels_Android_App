package com.myapp.project

class UserHistory {
    public lateinit var email:String
    lateinit var tripdetails:Trips
    public
    constructor() {

    }

    constructor(email: String, tripdetails: Trips) {
        this.email = email
        this.tripdetails = tripdetails
    }

}