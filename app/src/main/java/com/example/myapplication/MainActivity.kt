package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screen.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            runOnUiThread {
                Toast.makeText(this, "Erro: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                Log.e("APP_CRASH", "Erro não tratado:", e)
            }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "menu"
                    ) {
                        composable("menu") { MenuPrincipal(navController) }
                        composable("quizCam") { QuizCamScreen(navController) }
                        composable("turmas") { TurmasScreen(navController) }
                        composable("formularios") { ListaFormulariosScreen(navController) }
                        composable("questionario/{formularioId}") { backStackEntry ->
                            val formularioId = backStackEntry.arguments?.getString("formularioId")?.toIntOrNull() ?: 0
                            QuestionarioScreen(navController, formularioId)
                        }
                        composable("simularQuestionario/{formularioId}") { backStackEntry ->
                            val formularioId = backStackEntry.arguments?.getString("formularioId")?.toIntOrNull() ?: 0
                            SimularQuestionarioScreen(navController, formularioId)
                        }
                        composable("relatorio") { RelatorioScreen(navController) }
                        composable("alunos/{turmaId}") { backStackEntry ->
                            val turmaId = backStackEntry.arguments?.getString("turmaId")?.toIntOrNull() ?: 0
                            AlunosScreen(navController, turmaId)
                        }
                        composable("novoFormulario") { NovoFormularioScreen(navController) }
                    }
                }
            }
        }
    }
}