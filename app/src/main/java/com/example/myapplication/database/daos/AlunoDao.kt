package com.example.myapplication.database.daos

import androidx.room.*
import com.example.myapplication.database.entities.Aluno
import kotlinx.coroutines.flow.Flow

@Dao
interface AlunoDao {
    @Insert
    suspend fun insert(aluno: Aluno)

    @Update
    suspend fun update(aluno: Aluno)

    @Delete
    suspend fun delete(aluno: Aluno)

    @Query("SELECT * FROM alunos WHERE turmaId = :turmaId")
    fun getByTurma(turmaId: Int): Flow<List<Aluno>>

    @Query("SELECT * FROM alunos WHERE id = :id")
    suspend fun getById(id: Int): Aluno?

    @Query("SELECT COUNT(*) FROM alunos WHERE turmaId = :turmaId")
    suspend fun countByTurma(turmaId: Int): Int
}