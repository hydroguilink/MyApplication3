package com.example.myapplication.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "formularios")
data class Formulario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val descricao: String?,
    val dataCriacao: Long = System.currentTimeMillis()
)