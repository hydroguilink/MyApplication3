package com.example.myapplication.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.database.entities.Aluno
import com.example.myapplication.database.entities.Turma
import com.example.myapplication.repository.TurmaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TurmasViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TurmaRepository(application)

    // Turmas
    val allTurmas: Flow<List<Turma>> = repository.allTurmas

    fun addTurma(nome: String) {
        viewModelScope.launch {
            repository.insertTurma(Turma(nome = nome))
        }
    }

    fun deleteTurma(turma: Turma) {
        viewModelScope.launch {
            repository.deleteTurma(turma)
        }
    }

    // Alunos
    fun getAlunosByTurma(turmaId: Int): Flow<List<Aluno>> {
        return repository.getAlunosByTurma(turmaId)
    }

    fun addAluno(aluno: Aluno) {
        viewModelScope.launch {
            repository.insertAluno(aluno)
        }
    }

    fun deleteAluno(aluno: Aluno) {
        viewModelScope.launch {
            repository.deleteAluno(aluno)
        }
    }
}