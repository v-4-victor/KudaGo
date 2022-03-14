package com.example.kudago.di
import android.content.Context
import com.example.kudago.ApiModule
import com.example.kudago.ApiService
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ApiModule::class])
interface NormComponent {
    fun retrofit(): ApiService
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): NormComponent
    }
}