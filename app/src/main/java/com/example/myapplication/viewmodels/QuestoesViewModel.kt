package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.entities.Questao
import kotlinx.coroutines.launch

class QuestoesViewModel(
    private val db: AppDatabase,
    private val formularioId: Int = 0
) : ViewModel() {
    val questoes = if (formularioId == 0) {
        db.questaoDao().getAllQuestoes()
    } else {
        db.questaoDao().getQuestoesByFormulario(formularioId)
    }

    fun addQuestao(questao: Questao) {
        viewModelScope.launch {
            db.questaoDao().insert(questao)
        }
    }

    fun deleteQuestao(questao: Questao) {
        viewModelScope.launch {
            db.questaoDao().delete(questao)
        }
    }
}