package com.myapp.project

class WishListData {
    public lateinit var emailWish:String
    lateinit var tripdetailsWish:Trips

    public
    constructor() {

    }


    constructor(emailWish: String, tripdetailsWish: Trips) {
        this.emailWish = emailWish
        this.tripdetailsWish = tripdetailsWish
    }
}