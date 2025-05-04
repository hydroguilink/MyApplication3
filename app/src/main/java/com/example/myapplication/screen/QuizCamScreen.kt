package com.example.myapplication.screen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizCamScreen(navController: NavController) {
    val context = LocalContext.current
    var testResult by remember { mutableStateOf("Testando OpenCV...") }
    var testImage by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(Unit) {
        testOpenCV(context) { result, bitmap ->
            testResult = result
            testImage = bitmap
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Quiz Cam - Teste OpenCV") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(testResult, style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(16.dp))

            testImage?.let { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Teste OpenCV",
                    modifier = Modifier.size(200.dp)
                )
            }
        }
    }
}

private fun testOpenCV(context: Context, callback: (String, Bitmap?) -> Unit) {
    try {
        // 1. Verifica versão do OpenCV
        val version = Core.VERSION

        // 2. Cria uma imagem de teste
        val testBitmap = BitmapFactory.decodeResource(context.resources, android.R.drawable.ic_dialog_info)
        val mat = Mat()
        Utils.bitmapToMat(testBitmap, mat)

        // 3. Processamento simples (converte para escala de cinza)
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGBA2GRAY)

        // 4. Converte de volta para Bitmap
        val resultBitmap = Bitmap.createBitmap(testBitmap.width, testBitmap.height, Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(mat, resultBitmap)

        callback("OpenCV $version funcionando!", resultBitmap)
    } catch (e: Exception) {
        callback("Erro no OpenCV: ${e.message}", null)
    }
}