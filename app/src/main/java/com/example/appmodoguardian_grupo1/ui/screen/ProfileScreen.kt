package com.example.appmodoguardian_grupo1.ui.screen

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.appmodoguardian_grupo1.viewmodel.PerfilViewModel
import com.example.appmodoguardian_grupo1.viewmodel.UsuarioViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel,
    perfilViewModel: PerfilViewModel = viewModel()
) {
    val estado by usuarioViewModel.estado.collectAsState()
    val imagenUri by perfilViewModel.imagenUri.collectAsState()
    val context = LocalContext.current

    var mostrarCamara by remember { mutableStateOf(false) }
    val imageCapture = remember { ImageCapture.Builder().build() }

    var tienePermisoCamara by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { concedido ->
        tienePermisoCamara = concedido
        if (concedido) {
            mostrarCamara = true
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Perfil de Usuario") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (mostrarCamara && tienePermisoCamara) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    CameraPreview(
                        modifier = Modifier.fillMaxSize(),
                        imageCapture = imageCapture
                    )

                    Button(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp),
                        onClick = {
                            val uriTemporal = perfilViewModel.crearUriTemporal(context)
                            val directorio = File(context.cacheDir, "images").apply { mkdirs() }
                            val archivo = File(directorio, "foto_capturada.jpg")

                            val outputOptions = ImageCapture.OutputFileOptions.Builder(archivo).build()

                            imageCapture.takePicture(
                                outputOptions,
                                ContextCompat.getMainExecutor(context),
                                object : ImageCapture.OnImageSavedCallback {
                                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                        perfilViewModel.actualizarImagen(uriTemporal)
                                        mostrarCamara = false
                                    }

                                    override fun onError(exception: ImageCaptureException) {
                                        exception.printStackTrace()
                                    }
                                }
                            )
                        }
                    ) {
                        Text("Capturar Foto")
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    if (imagenUri != null) {
                        AsyncImage(
                            model = imagenUri,
                            contentDescription = "Foto de perfil",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Text(text = "Sin Foto")
                    }
                }

                Button(
                    onClick = {
                        if (tienePermisoCamara) {
                            mostrarCamara = true
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }
                ) {
                    Text("Abrir Cámara (CameraX API)")
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "Datos Registrados", style = MaterialTheme.typography.titleLarge)
                    HorizontalDivider()
                    Text(text = "Nombre: ${estado.nombre.ifBlank { "No registrado" }}")
                    Text(text = "Correo: ${estado.correo.ifBlank { "No registrado" }}")
                    Text(text = "Dirección: ${estado.direccion.ifBlank { "No registrada" }}")
                    Text(text = "Estado de términos: ${if (estado.aceptaTerminos) "Aceptados" else "No aceptados"}")
                }
            }

            Button(onClick = { navController.popBackStack("home", inclusive = false) }) {
                Text("Volver al Inicio")
            }
        }
    }
}