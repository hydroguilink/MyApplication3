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
import com.example.myapplication.screen.AlunosScreen
import com.example.myapplication.screen.MenuPrincipal
import com.example.myapplication.screen.QuestionarioScreen
import com.example.myapplication.screen.QuizCamScreen
import com.example.myapplication.screen.RelatorioScreen
import com.example.myapplication.screen.TurmasScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Configura o handler de erros globais ANTES de qualquer outra operação
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
                        composable("questionario") { QuestionarioScreen(navController) }
                        composable("relatorio") { RelatorioScreen(navController) }
                        composable("alunos/{turmaId}") { backStackEntry ->
                            val turmaId = backStackEntry.arguments?.getString("turmaId")?.toIntOrNull() ?: 0
                            AlunosScreen(
                                navController = navController,
                                turmaId = turmaId
                            )
                        }
                    }
                }
            }
        }
    }
}