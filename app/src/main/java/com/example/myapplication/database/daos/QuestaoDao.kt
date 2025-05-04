// Em QuestaoDao.kt
package com.example.myapplication.database.daos

import androidx.room.*
import com.example.myapplication.database.entities.Questao // ← Corrigido!
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestaoDao {
    @Insert
    suspend fun insert(questao: Questao)

    @Update
    suspend fun update(questao: Questao)

    @Delete
    suspend fun delete(questao: Questao)

    @Query("SELECT * FROM questoes ORDER BY id ASC")
    fun getAllQuestoes(): Flow<List<Questao>>

    @Query("SELECT * FROM questoes WHERE id = :id")
    suspend fun getQuestaoById(id: Int): Questao?

    @Query("SELECT * FROM questoes WHERE formularioId = :formularioId ORDER BY id ASC")
    fun getQuestoesByFormulario(formularioId: Int): Flow<List<Questao>>
}