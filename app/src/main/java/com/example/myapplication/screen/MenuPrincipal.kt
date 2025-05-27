package com.example.myapplication.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuPrincipal(navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { navController.navigate("quizCam") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text("📷 QUIZ CAM")
            }
            Button(
                onClick = { navController.navigate("turmas") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text("🎓 TURMAS")
            }
            Button(
                onClick = { navController.navigate("formularios") }, // Alterado para navegar para lista de formulários
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text("❓ QUESTIONÁRIO")
            }
            Button(
                onClick = { navController.navigate("relatorio") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("📊 RELATÓRIO")
            }
        }
    }
}