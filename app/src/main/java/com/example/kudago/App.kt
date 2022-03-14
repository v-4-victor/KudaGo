package com.example.kudago

import android.app.Application
import com.example.kudago.di.DaggerNormComponent
import com.example.kudago.di.NormComponent

open class App: Application() {

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: NormComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): NormComponent {
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerNormComponent.factory().create(applicationContext)
    }
}