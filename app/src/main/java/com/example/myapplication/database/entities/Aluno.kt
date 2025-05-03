package com.example.myapplication.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "alunos",
    foreignKeys = [
        ForeignKey(
            entity = Turma::class,
            parentColumns = ["id"],
            childColumns = ["turmaId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Aluno(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String = "",
    val turmaId: Int,
    val placa: String = "",
    val resultado: String = ""
)