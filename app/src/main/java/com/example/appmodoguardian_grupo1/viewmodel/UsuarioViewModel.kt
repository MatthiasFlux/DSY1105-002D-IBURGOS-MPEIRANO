package com.example.appmodoguardian_grupo1.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.appmodoguardian_grupo1.model.Rol
import com.example.appmodoguardian_grupo1.model.Usuario
import com.example.appmodoguardian_grupo1.model.UsuarioErrores
import com.example.appmodoguardian_grupo1.model.UsuarioUiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _estado = MutableStateFlow(UsuarioUiState())
    val estado: StateFlow<UsuarioUiState> = _estado

    private val usuariosPermitidos = listOf(
        Usuario("admin@guardian.test", "123456", Rol.ADMIN),
        Usuario("supervisor@guardian.test", "123456", Rol.SUPERVISOR),
        Usuario("operador@guardian.test", "123456", Rol.OPERADOR)
    )

    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChange(valor: String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    fun onDireccionChange(valor: String) {
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }

    fun onAceptaTerminosChange(valor: Boolean) {
        _estado.update { it.copy(aceptaTerminos = valor) }
    }

    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val errores = UsuarioErrores(
            nombre = if (estadoActual.nombre.isBlank()) "Campo obligatorio" else null,
            correo = if (!estadoActual.correo.contains("@")) "Correo inválido" else null,
            clave = if (estadoActual.clave.length < 6) "Debe tener al menos 6 caracteres" else null,
            direccion = if (estadoActual.direccion.isBlank()) "Campo obligatorio" else null
        )

        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
            errores.direccion
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }
        return !hayErrores
    }

    fun iniciarSesion(correoInput: String, claveInput: String): Rol? {
        val usuario = usuariosPermitidos.find {
            it.email.equals(correoInput.trim(), ignoreCase = true) && it.clave == claveInput
        }

        return if (usuario != null) {
            _estado.update { it.copy(correo = usuario.email) }
            usuario.rol
        } else {
            null
        }
    }

    // Redirección web directa a Google mediante Firebase Auth
    fun iniciarSesionConGoogleWeb(
        activity: Activity,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val provider = OAuthProvider.newBuilder("google.com").apply {
            addCustomParameter("prompt", "select_account")
        }.build()

        auth.startActivityForSignInWithProvider(activity, provider)
            .addOnSuccessListener { authResult ->
                val user = authResult.user
                _estado.update { actual ->
                    actual.copy(
                        correo = user?.email.orEmpty(),
                        nombre = user?.displayName ?: "Usuario Google",
                        aceptaTerminos = true
                    )
                }
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onError(exception.localizedMessage ?: "No se pudo completar el inicio con Google")
            }
    }

    fun cerrarSesion() {
        auth.signOut()
        _estado.value = UsuarioUiState()
    }
}