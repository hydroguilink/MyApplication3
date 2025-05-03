package com.example.myapplication.database.daos

import androidx.room.*
import com.example.myapplication.database.entities.Turma
import kotlinx.coroutines.flow.Flow

@Dao
interface TurmaDao {
    @Insert
    suspend fun insertTurma(turma: Turma)

    @Query("SELECT * FROM turmas")
    fun getAllTurmas(): Flow<List<Turma>>

    @Delete
    suspend fun deleteTurma(turma: Turma)
}