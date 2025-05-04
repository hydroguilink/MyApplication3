package com.example.myapplication.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questoes")
data class Questao(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val formularioId: Int,  // Adicione esta linha
    val texto: String,
    val imagemUri: String? = null, // Caminho ou URI da imagem (opcional)
    val alternativaA: String,
    val alternativaB: String,
    val alternativaC: String,
    val alternativaD: String,
    val correta: String // "A", "B", "C" ou "D"
)
