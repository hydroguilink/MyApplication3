package com.example.myapplication.screen

import android.app.Application
import android.util.Log
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
import com.example.myapplication.database.entities.Formulario
import com.example.myapplication.viewmodels.FormularioViewModel
import com.example.myapplication.viewmodels.FormularioViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaFormulariosScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: FormularioViewModel = viewModel(
        factory = FormularioViewModelFactory(
            context.applicationContext as Application
        )
    )

    // Usando collectAsState com Flow (correção do livedata)
    val formularios by viewModel.formularios.collectAsState(initial = emptyList())

    var showDialog by remember { mutableStateOf(false) }
    var nomeFormulario by remember { mutableStateOf("") }
    var descricaoFormulario by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Formulários") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                },
                actions = {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(Icons.Default.Add, "Adicionar formulário")
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
            if (formularios.isEmpty()) {
                Text(
                    "Nenhum formulário cadastrado",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn {
                    items(formularios) { formulario ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    navController.navigate("questionario/${formulario.id}")
                                }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        formulario.nome,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    formulario.descricao?.let {
                                        Text(it, style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            viewModel.deleteFormulario(formulario)
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

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Novo Formulário") },
            text = {
                Column {
                    TextField(
                        value = nomeFormulario,
                        onValueChange = { nomeFormulario = it },
                        label = { Text("Nome do formulário*") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = descricaoFormulario,
                        onValueChange = { descricaoFormulario = it },
                        label = { Text("Descrição (opcional)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (nomeFormulario.isNotBlank()) {
                            scope.launch {
                                try {
                                    viewModel.addFormulario(
                                        nomeFormulario,
                                        descricaoFormulario
                                    )
                                    nomeFormulario = ""
                                    descricaoFormulario = ""
                                    showDialog = false
                                } catch (e: Exception) {
                                    Log.e("Formulario", "Erro ao adicionar", e)
                                }
                            }
                        }
                    },
                    enabled = nomeFormulario.isNotBlank()
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