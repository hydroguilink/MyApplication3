package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.database.daos.AlunoDao
import com.example.myapplication.database.daos.FormularioDao // Import adicionado
import com.example.myapplication.database.daos.QuestaoDao
import com.example.myapplication.database.daos.TurmaDao
import com.example.myapplication.database.entities.Aluno
import com.example.myapplication.database.entities.Formulario // Import adicionado
import com.example.myapplication.database.entities.Questao
import com.example.myapplication.database.entities.Turma

@Database(
    entities = [Turma::class, Aluno::class, Questao::class, Formulario::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun turmaDao(): TurmaDao
    abstract fun alunoDao(): AlunoDao
    abstract fun questaoDao(): QuestaoDao
    abstract fun formularioDao(): FormularioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}