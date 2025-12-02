package com.example.pictureapi

import android.app.Application
import com.example.pictureapi.data.AppContainer
import com.example.pictureapi.data.DefaultAppContainer

class PhotosApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(applicationContext)
    }
}