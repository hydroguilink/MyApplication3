package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.myapplication.database.entities.Aluno
import com.example.myapplication.viewmodels.TurmasViewModel
import com.example.myapplication.viewmodels.TurmasViewModelFactory
import android.app.Application
import android.util.Log

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlunosScreen(
    navController: NavController,
    turmaId: Int
) {
    val context = LocalContext.current
    val viewModel: TurmasViewModel = viewModel(
        factory = TurmasViewModelFactory(
            context.applicationContext as Application
        )
    )

    val alunos = remember { mutableStateListOf<Aluno>() }
    var showDialog by remember { mutableStateOf(false) }
    var nome by remember { mutableStateOf("") }
    var placa by remember { mutableStateOf("") }

    LaunchedEffect(turmaId) {
        try {
            viewModel.getAlunosByTurma(turmaId).collect { lista ->
                alunos.clear()
                alunos.addAll(lista)
            }
        } catch (e: Exception) {
            Log.e("AlunosScreen", "Erro ao carregar alunos", e)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Alunos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(Icons.Default.Add, "Adicionar")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (alunos.isEmpty()) {
                Text(
                    "Nenhum aluno cadastrado",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn {
                    items(alunos) { aluno ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Nome: ${aluno.nome}")
                                Text("Placa: ${aluno.placa}")
                                IconButton(
                                    onClick = {
                                        viewModel.deleteAluno(aluno)
                                        alunos.remove(aluno)
                                    },
                                    modifier = Modifier.align(Alignment.End)
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

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Novo Aluno") },
            text = {
                Column {
                    TextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text("Nome") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = placa,
                        onValueChange = { placa = it },
                        label = { Text("Placa") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val novoAluno = Aluno(
                            nome = nome,
                            turmaId = turmaId,
                            placa = placa,
                            resultado = ""
                        )
                        viewModel.addAluno(novoAluno)
                        alunos.add(novoAluno)
                        nome = ""
                        placa = ""
                        showDialog = false
                    },
                    enabled = nome.isNotBlank() && placa.isNotBlank()
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