package com.example.appmodoguardian_grupo1.viewmodel

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

class PerfilViewModel : ViewModel() {

    // StateFlow para la URI de la imagen seleccionada o capturada
    private val _imagenUri = MutableStateFlow<Uri?>(null)
    val imagenUri: StateFlow<Uri?> = _imagenUri.asStateFlow()

    // Actualiza el estado cuando se selecciona de la galería o se toma con la cámara
    fun actualizarImagen(uri: Uri?) {
        if (uri != null) {
            _imagenUri.value = uri
        }
    }

    // Genera un archivo y URI temporal mediante FileProvider para almacenar la foto capturada por la cámara
    fun crearUriTemporal(context: Context): Uri {
        val directorio = File(context.cacheDir, "images").apply { mkdirs() }
        val archivo = File.createTempFile("foto_perfil_", ".jpg", directorio)
        val autoridad = "${context.packageName}.fileprovider"
        return FileProvider.getUriForFile(context, autoridad, archivo)
    }
}