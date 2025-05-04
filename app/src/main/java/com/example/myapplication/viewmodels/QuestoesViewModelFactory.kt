package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.database.AppDatabase

class QuestoesViewModelFactory(
    private val application: Application,
    private val formularioId: Int = 0
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestoesViewModel::class.java)) {
            val db = AppDatabase.getDatabase(application)
            @Suppress("UNCHECKED_CAST")
            return QuestoesViewModel(db, formularioId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}