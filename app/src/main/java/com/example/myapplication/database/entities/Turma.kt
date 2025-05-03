package com.example.myapplication.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "turmas")
data class Turma(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String
)