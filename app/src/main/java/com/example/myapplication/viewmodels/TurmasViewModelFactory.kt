package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TurmasViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TurmasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TurmasViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}