package com.example.myapplication.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.myapplication.viewmodels.TurmasViewModel
import com.example.myapplication.viewmodels.TurmasViewModelFactory
import android.app.Application
import android.widget.Toast
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TurmasScreen(
    navController: NavController
) {
    // 1. Inicialização do ViewModel e estados
    val context = LocalContext.current
    val viewModel: TurmasViewModel = viewModel(
        factory = TurmasViewModelFactory(
            context.applicationContext as Application
        )
    )

    val turmas by viewModel.allTurmas.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var novaTurma by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    // 2. Tratamento de erros global
    val exceptionHandler = remember {
        CoroutineExceptionHandler { _, throwable ->
            Log.e("TurmasScreen", "Erro: ${throwable.message}")
            Toast.makeText(context, "Erro ao carregar turmas", Toast.LENGTH_SHORT).show()
        }
    }

    // 3. Estrutura principal da tela
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Turmas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(Icons.Default.Add, "Adicionar turma")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (turmas.isEmpty()) {
                Text(
                    "Nenhuma turma cadastrada",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn {
                    items(turmas) { turma ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    try {
                                        navController.navigate("alunos/${turma.id}") {
                                            // Configurações importantes para evitar crashes
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            context,
                                            "Erro ao abrir turma",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        Log.e("NAVIGATION", "Erro: ${e.message}")
                                    }
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    turma.nome,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                IconButton(
                                    onClick = {
                                        scope.launch(exceptionHandler) {
                                            viewModel.deleteTurma(turma)
                                        }
                                    }
                                ) {
                                    Icon(Icons.Default.Delete, "Excluir")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // 4. Dialog para nova turma
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Nova Turma") },
            text = {
                TextField(
                    value = novaTurma,
                    onValueChange = { novaTurma = it },
                    label = { Text("Nome da turma") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (novaTurma.isNotBlank()) {
                            scope.launch(exceptionHandler) {
                                viewModel.addTurma(novaTurma)
                                novaTurma = ""
                                showDialog = false
                            }
                        }
                    },
                    enabled = novaTurma.isNotBlank()
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