package com.example.myapplication.screen

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.database.entities.Questao
import com.example.myapplication.viewmodels.QuestoesViewModel
import com.example.myapplication.viewmodels.QuestoesViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionarioScreen(
    navController: NavController,
    formularioId: Int
) {
    val context = LocalContext.current
    val viewModel: QuestoesViewModel = viewModel(
        factory = QuestoesViewModelFactory(
            context.applicationContext as Application,
            formularioId
        )
    )

    val questoes by viewModel.questoes.collectAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var textoQuestao by remember { mutableStateOf("") }
    var alternativaA by remember { mutableStateOf("") }
    var alternativaB by remember { mutableStateOf("") }
    var alternativaC by remember { mutableStateOf("") }
    var alternativaD by remember { mutableStateOf("") }
    var correta by remember { mutableStateOf("A") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Questões do Formulário") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(Icons.Default.Add, "Adicionar questão")
                    }
                }
            )
        },
        floatingActionButton = {
            if (questoes.isNotEmpty()) {
                FloatingActionButton(
                    onClick = { navController.navigate("simularQuestionario/$formularioId") }
                ) {
                    Icon(Icons.Default.PlayArrow, "Simular")
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (questoes.isEmpty()) {
                Text(
                    "Nenhuma questão cadastrada",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(questoes) { questao ->
                        QuestaoItem(
                            questao = questao,
                            onDelete = { viewModel.deleteQuestao(questao) }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Nova Questão") },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        value = textoQuestao,
                        onValueChange = { textoQuestao = it },
                        label = { Text("Texto da questão*") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = alternativaA,
                        onValueChange = { alternativaA = it },
                        label = { Text("Alternativa A*") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = alternativaB,
                        onValueChange = { alternativaB = it },
                        label = { Text("Alternativa B*") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = alternativaC,
                        onValueChange = { alternativaC = it },
                        label = { Text("Alternativa C*") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = alternativaD,
                        onValueChange = { alternativaD = it },
                        label = { Text("Alternativa D*") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Alternativa correta*:")
                    Row {
                        listOf("A", "B", "C", "D").forEach { opcao ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { correta = opcao }
                            ) {
                                RadioButton(
                                    selected = correta == opcao,
                                    onClick = { correta = opcao }
                                )
                                Text(opcao)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val novaQuestao = Questao(
                            formularioId = formularioId,
                            texto = textoQuestao,
                            alternativaA = alternativaA,
                            alternativaB = alternativaB,
                            alternativaC = alternativaC,
                            alternativaD = alternativaD,
                            correta = correta
                        )
                        viewModel.addQuestao(novaQuestao)
                        // Resetar campos
                        textoQuestao = ""
                        alternativaA = ""
                        alternativaB = ""
                        alternativaC = ""
                        alternativaD = ""
                        correta = "A"
                        showDialog = false
                    },
                    enabled = textoQuestao.isNotBlank() &&
                            alternativaA.isNotBlank() &&
                            alternativaB.isNotBlank() &&
                            alternativaC.isNotBlank() &&
                            alternativaD.isNotBlank()
                ) {
                    Text("Adicionar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun QuestaoItem(questao: Questao, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(questao.texto, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("A) ${questao.alternativaA}")
            Text("B) ${questao.alternativaB}")
            Text("C) ${questao.alternativaC}")
            Text("D) ${questao.alternativaD}")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Correta: ${questao.correta}", style = MaterialTheme.typography.labelMedium)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Excluir")
                }
            }
        }
    }
}