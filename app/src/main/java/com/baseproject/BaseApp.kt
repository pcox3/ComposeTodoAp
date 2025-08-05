package com.baseproject

import android.app.Application
import com.baseproject.db.TaskDatabase

class BaseApp: Application() {

    override fun onCreate() {
        super.onCreate()
       lazy { TaskDatabase.getInstance(this) }

    }



}