package com.example.myapplication.database.daos

import androidx.room.*
import com.example.myapplication.database.entities.Formulario
import kotlinx.coroutines.flow.Flow

@Dao
interface FormularioDao {
    @Insert
    suspend fun insert(formulario: Formulario)

    @Query("SELECT * FROM formularios ORDER BY dataCriacao DESC")
    fun getAllFormularios(): Flow<List<Formulario>>

    @Query("SELECT * FROM formularios WHERE id = :id")
    suspend fun getFormularioById(id: Int): Formulario?

    @Delete
    suspend fun delete(formulario: Formulario)

}