package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.database.AppDatabase

class FormularioViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormularioViewModel::class.java)) {
            val db = AppDatabase.getDatabase(application)
            @Suppress("UNCHECKED_CAST")
            return FormularioViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}