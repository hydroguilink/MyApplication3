package com.example.myapplication.repository

import android.app.Application
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.entities.Aluno
import com.example.myapplication.database.entities.Turma
import kotlinx.coroutines.flow.Flow

class TurmaRepository(application: Application) {
    private val db = AppDatabase.getDatabase(application)
    private val turmaDao = db.turmaDao()
    private val alunoDao = db.alunoDao()

    // Turmas
    val allTurmas: Flow<List<Turma>> = turmaDao.getAllTurmas()

    suspend fun insertTurma(turma: Turma) {
        turmaDao.insertTurma(turma)
    }

    suspend fun deleteTurma(turma: Turma) {
        turmaDao.deleteTurma(turma)
    }

    // Alunos
    fun getAlunosByTurma(turmaId: Int): Flow<List<Aluno>> {
        return alunoDao.getByTurma(turmaId)
    }

    suspend fun insertAluno(aluno: Aluno) {
        alunoDao.insert(aluno)
    }

    suspend fun deleteAluno(aluno: Aluno) {
        alunoDao.delete(aluno)
    }
}