package com.myapp.project

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater

class LoadingDialog
{
    private var activity:Activity
    private lateinit var dialog:AlertDialog

    constructor(activity: Activity) {
        this.activity = activity
    }

    fun startLoading()
    {
        var builder = AlertDialog.Builder(activity)
        var inflater:LayoutInflater = activity.layoutInflater

        builder.setView(inflater.inflate(R.layout.custom_dialog,null))
        builder.setCancelable(false)

        dialog=builder.create()
        dialog.show()
    }

    fun dismissDialog()
    {
        dialog.dismiss()
    }
}