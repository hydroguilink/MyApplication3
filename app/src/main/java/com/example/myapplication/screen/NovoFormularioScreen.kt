package com.example.myapplication.screen

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.viewmodels.FormularioViewModel
import com.example.myapplication.viewmodels.FormularioViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovoFormularioScreen(navController: NavController) {
    val context = LocalContext.current
    val viewModel: FormularioViewModel = viewModel(
        factory = FormularioViewModelFactory(
            context.applicationContext as Application
        )
    )
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Novo Formulário") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            TextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome do Formulário") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição (opcional)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    viewModel.addFormulario(nome, descricao)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = nome.isNotBlank()
            ) {
                Text("Salvar Formulário")
            }
        }
    }
}