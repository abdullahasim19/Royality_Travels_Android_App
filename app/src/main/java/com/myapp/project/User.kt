package com.myapp.project

import java.io.Serializable

class User :Serializable{
    public lateinit var email:String
    public lateinit var password:String
    public lateinit var userNameTrip:String
    public lateinit var phoneNumber:String

    public
    constructor() {
        email=" "
        password=" "
        userNameTrip=" "
        phoneNumber=" "
    }

    constructor(email: String, password: String, userNameTrip: String, phoneNumber: String) {
        this.email = email
        this.password = password
        this.userNameTrip = userNameTrip
        this.phoneNumber = phoneNumber
    }


}