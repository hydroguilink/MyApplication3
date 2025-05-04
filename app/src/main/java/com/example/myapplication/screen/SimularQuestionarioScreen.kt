package com.example.myapplication.screen

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.viewmodels.QuestoesViewModel
import com.example.myapplication.viewmodels.QuestoesViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimularQuestionarioScreen(
    navController: NavController,
    formularioId: Int = 0
) {
    val context = LocalContext.current
    val viewModel: QuestoesViewModel = viewModel(
        factory = QuestoesViewModelFactory(
            context.applicationContext as Application,
            formularioId
        )
    )

    val questoes by viewModel.questoes.collectAsState(emptyList())
    var currentIndex by remember { mutableStateOf(0) }
    var respostaSelecionada by remember { mutableStateOf<String?>(null) }
    var mostrarResultado by remember { mutableStateOf(false) }

    if (questoes.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Nenhuma questão cadastrada")
            Button(onClick = { navController.popBackStack() }) {
                Text("Voltar")
            }
        }
        return
    }

    val questaoAtual = questoes[currentIndex]

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Questão ${currentIndex + 1}/${questoes.size}") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                questaoAtual.texto,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            val alternativas = listOf(
                "A" to questaoAtual.alternativaA,
                "B" to questaoAtual.alternativaB,
                "C" to questaoAtual.alternativaC,
                "D" to questaoAtual.alternativaD
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                alternativas.forEach { (letra, texto) ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            if (!mostrarResultado) {
                                respostaSelecionada = letra
                            }
                        }
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = respostaSelecionada == letra,
                                onClick = null
                            )
                            Text(
                                "$letra) $texto",
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            if (mostrarResultado) {
                val acertou = respostaSelecionada == questaoAtual.correta
                Text(
                    if (acertou) "✅ Resposta correta!" else "❌ Resposta incorreta!",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (acertou) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            Button(
                onClick = {
                    if (respostaSelecionada == null) return@Button

                    if (!mostrarResultado) {
                        mostrarResultado = true
                    } else {
                        if (currentIndex < questoes.size - 1) {
                            currentIndex++
                            respostaSelecionada = null
                            mostrarResultado = false
                        } else {
                            navController.popBackStack()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = respostaSelecionada != null
            ) {
                Text(
                    when {
                        !mostrarResultado -> "Verificar resposta"
                        currentIndex < questoes.size - 1 -> "Próxima questão"
                        else -> "Finalizar"
                    }
                )
            }
        }
    }
}