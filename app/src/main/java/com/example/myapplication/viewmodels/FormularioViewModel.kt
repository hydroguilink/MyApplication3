package com.example.myapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.entities.Formulario
import kotlinx.coroutines.launch

class FormularioViewModel(private val db: AppDatabase) : ViewModel() {
    // Correção 4: Propriedade 'formularios' definida corretamente
    val formularios = db.formularioDao().getAllFormularios()

    // Correção 5: Método para deletar formulário
    fun deleteFormulario(formulario: Formulario) {
        viewModelScope.launch {
            db.formularioDao().delete(formulario)
        }
    }

    fun addFormulario(nome: String, descricao: String?) {
        viewModelScope.launch {
            db.formularioDao().insert(
                Formulario(
                    nome = nome,
                    descricao = descricao,
                    dataCriacao = System.currentTimeMillis()
                )
            )
        }
    }
}