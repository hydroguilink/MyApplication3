package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screen.*
import org.opencv.android.OpenCVLoader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current

            // Verifica se o OpenCV está carregado
            LaunchedEffect(Unit) {
                if (OpenCVLoader.initDebug()) {
                    Toast.makeText(context, "OpenCV carregado com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Falha ao carregar OpenCV", Toast.LENGTH_LONG).show()
                }
            }

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
                        composable("relatorio") { RelatorioScreen(navController) }
                        composable("formularios") { ListaFormulariosScreen(navController) }
                        composable("novoFormulario") { NovoFormularioScreen(navController) }
                        composable("questionario/{formularioId}") { backStackEntry ->
                            val formularioId = backStackEntry.arguments?.getString("formularioId")?.toIntOrNull() ?: 0
                            QuestionarioScreen(navController, formularioId)
                        }
                        composable("simularQuestionario/{formularioId}") { backStackEntry ->
                            val formularioId = backStackEntry.arguments?.getString("formularioId")?.toIntOrNull() ?: 0
                            SimularQuestionarioScreen(navController, formularioId)
                        }
                        composable("alunos/{turmaId}") { backStackEntry ->
                            val turmaId = backStackEntry.arguments?.getString("turmaId")?.toIntOrNull() ?: 0
                            AlunosScreen(navController, turmaId)
                        }
                    }
                }
            }
        }
    }
}